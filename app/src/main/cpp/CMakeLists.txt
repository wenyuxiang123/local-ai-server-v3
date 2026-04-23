cmake_minimum_required(VERSION 3.10.2)
project("localai-jni")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# 使用预编译的llama.cpp库（由GitHub Actions构建）
# 库文件位于 app/src/main/jniLibs/arm64-v8a/

# 设置预编译库路径
set(LIB_DIR ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

# 导入预编译库
add_library(ggml-base SHARED IMPORTED)
set_target_properties(ggml-base PROPERTIES IMPORTED_LOCATION ${LIB_DIR}/libggml-base.so)

add_library(ggml-cpu SHARED IMPORTED)
set_target_properties(ggml-cpu PROPERTIES IMPORTED_LOCATION ${LIB_DIR}/libggml-cpu.so)

add_library(ggml SHARED IMPORTED)
set_target_properties(ggml PROPERTIES IMPORTED_LOCATION ${LIB_DIR}/libggml.so)

add_library(llama SHARED IMPORTED)
set_target_properties(llama PROPERTIES IMPORTED_LOCATION ${LIB_DIR}/libllama.so)

# 头文件目录（llama.cpp headers 由 GitHub Actions 复制）
set(LLAMA_INCLUDE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/llama-src/include)

# JNI库
add_library(localai-jni SHARED
    native-lib.cpp
)

target_include_directories(localai-jni PRIVATE
    ${LLAMA_INCLUDE_DIR}
)

target_link_libraries(localai-jni
    llama
    ggml
    ggml-cpu
    ggml-base
    log
    android
)
