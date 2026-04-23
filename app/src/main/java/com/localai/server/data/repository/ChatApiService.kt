package com.localai.server.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatRequest(
    val model: String = "qwen3",
    val messages: List<ChatMessage>,
    val max_tokens: Int = 2048,
    val temperature: Float = 0.7f,
    val stream: Boolean = false
)

data class ChatResponse(
    val id: String?,
    val choices: List<Choice>?,
    val error: ErrorResponse?
)

data class Choice(
    val message: MessageContent?
)

data class MessageContent(
    val role: String?,
    val content: String?
)

data class ErrorResponse(
    val message: String?,
    val type: String?
)

@Singleton
class ChatApiService @Inject constructor() {
    
    companion object {
        private const val TAG = "ChatApiService"
        private const val DEFAULT_BASE_URL = "http://localhost:8080"
    }
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    
    private val gson = Gson()
    
    suspend fun sendMessage(
        baseUrl: String = DEFAULT_BASE_URL,
        messages: List<ChatMessage>
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val requestBody = ChatRequest(
                messages = messages,
                max_tokens = 2048,
                temperature = 0.7f
            )
            
            val json = gson.toJson(requestBody)
            Log.d(TAG, "Request: $json")
            
            val request = Request.Builder()
                .url("$baseUrl/v1/chat/completions")
                .post(json.toRequestBody("application/json".toMediaType()))
                .build()
            
            client.newCall(request).execute().use { response ->
                val body = response.body?.string()
                Log.d(TAG, "Response code: ${response.code}")
                Log.d(TAG, "Response body: $body")
                
                if (!response.isSuccessful) {
                    return@withContext Result.failure(
                        IOException("HTTP ${response.code}: ${response.message}")
                    )
                }
                
                body?.let {
                    val chatResponse = gson.fromJson(it, ChatResponse::class.java)
                    
                    // Check for API error
                    chatResponse.error?.let { error ->
                        return@withContext Result.failure(
                            IOException(error.message ?: "API Error")
                        )
                    }
                    
                    // Extract assistant message
                    val assistantMessage = chatResponse.choices
                        ?.firstOrNull()
                        ?.message
                        ?.content
                        ?: ""
                    
                    return@withContext Result.success(assistantMessage)
                }
                
                Result.failure(IOException("Empty response body"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message", e)
            Result.failure(e)
        }
    }
    
    // Build messages for API from database messages
    fun buildMessages(userContent: String, historyMessages: List<ChatMessage>): List<ChatMessage> {
        val allMessages = historyMessages.toMutableList()
        
        // Add enable thinking message for Qwen3
        if (allMessages.isEmpty()) {
            allMessages.add(ChatMessage("system", "You are a helpful assistant."))
        }
        
        allMessages.add(ChatMessage("user", userContent))
        
        return allMessages
    }
}
