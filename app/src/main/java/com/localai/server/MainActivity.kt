package com.localai.server

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.localai.server.databinding.ActivityMainBinding
import com.localai.server.domain.model.AVAILABLE_MODELS
import com.localai.server.engine.LlamaEngine
import com.localai.server.ui.chat.ChatActivity
import com.localai.server.ui.main.MainEffect
import com.localai.server.ui.main.MainIntent
import com.localai.server.ui.main.MainState
import com.localai.server.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var selectedModelUri: Uri? = null
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (!allGranted) {
            showToast("需要权限才能正常使用")
        }
    }
    
    private val selectModelLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            selectedModelUri = it
            val fileName = it.lastPathSegment?.substringAfterLast("/") ?: "选中文件"
            binding.tvSelectedModel.text = fileName
            showToast("已选择: $fileName")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 检查native库是否可用
        checkNativeLibraries()
        
        checkPermissions()
        setupViews()
        observeState()
        observeEffects()
    }
    
    private fun checkNativeLibraries() {
        try {
            val loaded = LlamaEngine.loadLibraries()
            if (!loaded) {
                val error = LlamaEngine.getLoadError() ?: "Unknown error"
                Log.e(TAG, "Native library check failed: $error")
                showToast("Native库加载失败: $error")
            } else {
                Log.i(TAG, "Native libraries loaded successfully")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to check native libraries", e)
            showToast("检查Native库时出错: ${e.message}")
        }
    }
    
    private fun checkPermissions() {
        val permissions = mutableListOf<String>()
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        
        if (permissions.isNotEmpty()) {
            requestPermissionLauncher.launch(permissions.toTypedArray())
        }
    }
    
    private fun setupViews() {
        // 服务控制
        binding.btnStart.setOnClickListener {
            viewModel.onIntent(MainIntent.StartService)
        }
        
        binding.btnStop.setOnClickListener {
            viewModel.onIntent(MainIntent.StopService)
        }
        
        // 模型选择
        val modelNames = AVAILABLE_MODELS.map { "${it.name} (${it.size})" }
        val adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, modelNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerModels.adapter = adapter
        
        binding.btnDownload.setOnClickListener {
            val index = binding.spinnerModels.selectedItemPosition
            if (index in AVAILABLE_MODELS.indices) {
                val model = AVAILABLE_MODELS[index]
                viewModel.onIntent(MainIntent.DownloadModel(model.url, model.name))
            }
        }
        
        // 加载模型
        binding.btnSelectModel.setOnClickListener {
            selectModelLauncher.launch(arrayOf("*/*"))
        }
        
        binding.btnLoadModel.setOnClickListener {
            val uri = selectedModelUri
            if (uri != null) {
                viewModel.onIntent(MainIntent.LoadModel(uri.toString()))
            } else {
                showToast("请先选择模型文件")
            }
        }
        
        // 聊天按钮
        binding.btnChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }
    }
    
    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    updateUI(state)
                }
            }
        }
    }
    
    private fun observeEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is MainEffect.ShowToast -> showToast(effect.message)
                        is MainEffect.ShowError -> showError(effect.message)
                    }
                }
            }
        }
    }
    
    private fun updateUI(state: MainState) {
        binding.apply {
            // 模型下载进度
            cardDownloadProgress.isVisible = state.isDownloadingModel || state.downloadStatus.isNotEmpty()
            if (state.isDownloadingModel) {
                tvDownloadStatus.text = state.downloadStatus
                progressDownload.progress = state.downloadPercent
                tvDownloadPercent.text = "${state.downloadPercent}%"
                tvDownloadSpeed.text = formatSpeed(state.downloadSpeed)
            } else if (state.modelReady) {
                cardDownloadProgress.isVisible = false
            }
            
            // 服务状态
            tvStatus.text = when {
                state.serviceRunning && state.modelLoaded -> "运行中"
                state.serviceRunning -> "服务启动，等待加载模型"
                else -> "已停止"
            }
            
            tvStatus.setBackgroundResource(
                if (state.serviceRunning && state.modelLoaded) 
                    R.color.status_running 
                else 
                    R.color.status_stopped
            )
            
            // 服务器地址
            tvAddress.text = if (state.serverAddress.isNotEmpty()) {
                "API地址: ${state.serverAddress}"
            } else {
                "API地址: --"
            }
            
            // 模型状态
            tvModelStatus.text = when {
                state.modelReady -> "模型: Qwen3-1.7B 已就绪"
                state.modelLoaded -> "模型: ${state.modelConfig?.name ?: "已加载"}"
                else -> "模型: 未加载"
            }
            
            // 按钮状态
            btnStart.isEnabled = !state.serviceRunning && state.modelReady
            btnStop.isEnabled = state.serviceRunning
            btnLoadModel.isVisible = false // 隐藏手动加载按钮
            btnSelectModel.isVisible = false // 隐藏选择文件按钮
            
            // 聊天按钮 - 只在服务运行时可用
            btnChat.isEnabled = state.serviceRunning && state.modelLoaded
            
            // 模型加载进度
            progressBar.isVisible = state.isLoading || state.isDownloading || state.isModelLoading
            if (state.isDownloading) {
                progressBar.progress = state.downloadProgress
            } else if (state.isModelLoading) {
                progressBar.progress = state.modelLoadingProgress
            }
            
            // 模型加载状态文本
            if (state.isModelLoading) {
                tvStatus.text = "加载中... ${state.modelLoadingProgress}%"
            }
            
            // 错误信息
            tvError.isVisible = state.error != null
            tvError.text = state.error
        }
    }
    
    private fun formatSpeed(bytesPerSecond: Long): String {
        return when {
            bytesPerSecond >= 1024 * 1024 -> String.format("%.1f MB/s", bytesPerSecond / (1024.0 * 1024.0))
            bytesPerSecond >= 1024 -> String.format("%.1f KB/s", bytesPerSecond / 1024.0)
            else -> "$bytesPerSecond B/s"
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun showError(message: String) {
        Toast.makeText(this, "错误: $message", Toast.LENGTH_LONG).show()
    }
}
