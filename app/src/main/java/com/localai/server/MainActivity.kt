package com.localai.server

import android.Manifest
import android.content.pm.PackageManager
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
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (!allGranted) {
            showToast("需要权限才能正常使用")
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
        binding.btnLoadModel.setOnClickListener {
            val path = binding.tvSelectedModel.text.toString()
            if (path.isNotEmpty()) {
                viewModel.onIntent(MainIntent.LoadModel(path))
            }
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
            tvModelStatus.text = if (state.modelLoaded) {
                "模型: ${state.modelConfig?.name ?: "已加载"}"
            } else {
                "模型: 未加载"
            }
            
            // 按钮状态
            btnStart.isEnabled = !state.serviceRunning && state.modelLoaded
            btnStop.isEnabled = state.serviceRunning
            btnLoadModel.isEnabled = state.serviceRunning && !state.modelLoaded
            
            // 进度
            progressBar.isVisible = state.isLoading || state.isDownloading
            if (state.isDownloading) {
                progressBar.progress = state.downloadProgress
            }
            
            // 错误信息
            tvError.isVisible = state.error != null
            tvError.text = state.error
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun showError(message: String) {
        Toast.makeText(this, "错误: $message", Toast.LENGTH_LONG).show()
    }
}
