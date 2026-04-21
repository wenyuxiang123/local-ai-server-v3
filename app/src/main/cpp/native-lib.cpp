#include <jni.h>
#include <string>
#include <vector>
#include <android/log.h>
#include "llama.h"

#define TAG "LlamaEngine"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

// 全局模型和上下文
static llama_model* g_model = nullptr;
static llama_context* g_ctx = nullptr;
static std::string g_model_path;
static std::string g_model_name;

extern "C" {

JNIEXPORT jboolean JNICALL
Java_com_localai_server_engine_LlamaEngine_loadModelNative(
        JNIEnv* env, jobject thiz,
        jstring model_path, jint n_ctx, jint n_threads) {
    
    const char* path = env->GetStringUTFChars(model_path, nullptr);
    LOGI("Loading model from: %s", path);
    
    // 释放旧模型
    if (g_model) {
        llama_model_free(g_model);
        g_model = nullptr;
    }
    if (g_ctx) {
        llama_free(g_ctx);
        g_ctx = nullptr;
    }
    
    // 模型参数
    llama_model_params mparams = llama_model_default_params();
    mparams.n_gpu_layers = 0; // CPU only
    
    // 加载模型
    g_model = llama_model_load_from_file(path, mparams);
    if (!g_model) {
        LOGE("Failed to load model from: %s", path);
        env->ReleaseStringUTFChars(model_path, path);
        return JNI_FALSE;
    }
    
    // 上下文参数
    llama_context_params cparams = llama_context_default_params();
    cparams.n_ctx = n_ctx;
    cparams.n_threads = n_threads;
    cparams.n_threads_batch = n_threads;
    
    // 创建上下文
    g_ctx = llama_init_from_model(g_model, cparams);
    if (!g_ctx) {
        LOGE("Failed to create context");
        llama_model_free(g_model);
        g_model = nullptr;
        env->ReleaseStringUTFChars(model_path, path);
        return JNI_FALSE;
    }
    
    g_model_path = std::string(path);
    g_model_name = std::string(path).substr(std::string(path).find_last_of("/\\") + 1);
    
    LOGI("Model loaded successfully: %s", g_model_name.c_str());
    env->ReleaseStringUTFChars(model_path, path);
    return JNI_TRUE;
}

JNIEXPORT void JNICALL
Java_com_localai_server_engine_LlamaEngine_unloadModelNative(
        JNIEnv* env, jobject thiz) {
    
    LOGI("Unloading model");
    
    if (g_ctx) {
        llama_free(g_ctx);
        g_ctx = nullptr;
    }
    if (g_model) {
        llama_model_free(g_model);
        g_model = nullptr;
    }
    
    g_model_path.clear();
    g_model_name.clear();
}

JNIEXPORT jboolean JNICALL
Java_com_localai_server_engine_LlamaEngine_isModelLoadedNative(
        JNIEnv* env, jobject thiz) {
    return (g_model != nullptr && g_ctx != nullptr) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jstring JNICALL
Java_com_localai_server_engine_LlamaEngine_generateNative(
        JNIEnv* env, jobject thiz,
        jstring prompt, jint max_tokens, jfloat temperature, jint top_k, jfloat top_p) {
    
    if (!g_model || !g_ctx) {
        LOGE("Model not loaded");
        return env->NewStringUTF("");
    }
    
    const char* prompt_str = env->GetStringUTFChars(prompt, nullptr);
    LOGD("Generating text for prompt: %s", prompt_str);
    
    // Tokenize
    const bool add_bos = true;
    std::vector<llama_token> tokens;
    tokens.resize(strlen(prompt_str) + 1);
    int n_tokens = llama_tokenize(g_model, prompt_str, strlen(prompt_str), tokens.data(), tokens.size(), add_bos, false);
    tokens.resize(n_tokens);
    
    // 采样参数
    llama_sampler_chain_params sparams = llama_sampler_chain_default_params();
    llama_sampler* sampler = llama_sampler_chain_init(sparams);
    llama_sampler_chain_add(sampler, llama_sampler_init_top_k(top_k));
    llama_sampler_chain_add(sampler, llama_sampler_init_top_p(top_p, 1));
    llama_sampler_chain_add(sampler, llama_sampler_init_temp(temperature));
    llama_sampler_chain_add(sampler, llama_sampler_init_dist(LLAMA_DEFAULT_SEED));
    
    // 生成
    std::string result;
    for (int i = 0; i < max_tokens; i++) {
        if (llama_decode(g_ctx, llama_batch_get_one(tokens.data(), tokens.size())) != 0) {
            LOGE("Failed to decode");
            break;
        }
        
        // 获取下一个token
        llama_token new_token = llama_sampler_sample(sampler, g_ctx, -1);
        
        // 检查EOS
        const llama_vocab* vocab = llama_model_get_vocab(g_model);
        if (llama_vocab_is_eog(vocab, new_token)) {
            break;
        }
        
        // 转换为文本
        char buf[256];
        int n = llama_token_to_piece(vocab, new_token, buf, sizeof(buf), 0, false);
        if (n > 0) {
            result.append(buf, n);
        }
        
        tokens.clear();
        tokens.push_back(new_token);
    }
    
    llama_sampler_free(sampler);
    env->ReleaseStringUTFChars(prompt, prompt_str);
    
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_localai_server_engine_LlamaEngine_getLoadedModelNameNative(
        JNIEnv* env, jobject thiz) {
    return env->NewStringUTF(g_model_name.c_str());
}

JNIEXPORT jint JNICALL
Java_com_localai_server_engine_LlamaEngine_getContextSizeNative(
        JNIEnv* env, jobject thiz) {
    if (!g_ctx) return 0;
    return llama_n_ctx(g_ctx);
}

JNIEXPORT jlong JNICALL
Java_com_localai_server_engine_LlamaEngine_getMemoryUsageNative(
        JNIEnv* env, jobject thiz) {
    if (!g_model) return 0;
    // 返回估计的内存使用量
    return llama_model_size(g_model);
}

}
