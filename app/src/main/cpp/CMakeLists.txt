cmake_minimum_required(VERSION 3.10.2)
project("localai-jni")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# llama.cpp源码目录
set(LLAMA_SRC_DIR ${CMAKE_CURRENT_SOURCE_DIR}/llama-src)

# 添加llama.cpp作为子目录（仅编译库）
add_library(llama STATIC
    ${LLAMA_SRC_DIR}/src/llama.cpp
    ${LLAMA_SRC_DIR}/src/llama-impl.cpp
    ${LLAMA_SRC_DIR}/src/llama-context.cpp
    ${LLAMA_SRC_DIR}/src/llama-model.cpp
    ${LLAMA_SRC_DIR}/src/llama-vocab.cpp
    ${LLAMA_SRC_DIR}/src/llama-grammar.cpp
    ${LLAMA_SRC_DIR}/src/llama-sampling.cpp
    ${LLAMA_SRC_DIR}/src/llama-chat.cpp
    ${LLAMA_SRC_DIR}/ggml/src/ggml.c
    ${LLAMA_SRC_DIR}/ggml/src/ggml-alloc.c
    ${LLAMA_SRC_DIR}/ggml/src/ggml-backend.cpp
    ${LLAMA_SRC_DIR}/ggml/src/ggml-quants.c
    ${LLAMA_SRC_DIR}/ggml/src/ggml-cpu/ggml-cpu.c
    ${LLAMA_SRC_DIR}/ggml/src/ggml-cpu/ggml-cpu.cpp
)

target_include_directories(llama PUBLIC
    ${LLAMA_SRC_DIR}/include
    ${LLAMA_SRC_DIR}/ggml/include
    ${LLAMA_SRC_DIR}/ggml/src
    ${LLAMA_SRC_DIR}/ggml/src/ggml-cpu
)

# JNI库
add_library(localai-jni SHARED
    native-lib.cpp
)

target_include_directories(localai-jni PRIVATE
    ${LLAMA_SRC_DIR}/include
)

target_link_libraries(localai-jni
    llama
    log
    android
)
