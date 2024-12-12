package com.example.allergifyapp.ui.main

import android.Manifest
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityMainBinding
import com.example.allergifyapp.databinding.PopUpRejectCameraPermissionBinding
import com.example.allergifyapp.ui.camera.CameraActivity
import com.example.allergifyapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var popUpRejectCameraBinding: PopUpRejectCameraPermissionBinding
    private lateinit var popUpRejectCameraDialog: Dialog
    private val authViewModel: AuthViewModel by viewModels()
    private val requestCodeCamera = 100
    private var permissionGranted = false
    private var permissionDenied = false
    private var isReturningFromSettings = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupCheckIfLoggedIn()
        setupBottomNavigation()
        setupScanButton()
        setupRejectCameraPopUp()

    }

    private fun setupCheckIfLoggedIn() {
        if (!authViewModel.isLoggedIn()) {
            startActivity(Intent(this, IntroScreenActivity::class.java))
            finish()
        }

    }

    private fun setupScanButton() {
        binding.scanButton.setOnClickListener {
            if (permissionGranted) {
                startCameraActivity()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), requestCodeCamera)
    }

    private fun startCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
    }

    private fun setupRejectCameraPopUp() {
        popUpRejectCameraBinding = PopUpRejectCameraPermissionBinding.inflate(layoutInflater)

        popUpRejectCameraDialog = Dialog(this)
        popUpRejectCameraDialog.setContentView(popUpRejectCameraBinding.root)
        popUpRejectCameraDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        popUpRejectCameraDialog.setCancelable(false)

        popUpRejectCameraBinding.cancelButton.setOnClickListener {
            popUpRejectCameraDialog.dismiss()
        }

        popUpRejectCameraBinding.openSettingButton.setOnClickListener {
            isReturningFromSettings = true
            openAppSettings()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCamera) {
            permissionGranted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            permissionDenied = !permissionGranted

            if (permissionGranted) {
                startCameraActivity()
            } else {
                permissionDenied = true
                showRejectCameraPopUp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        permissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        permissionDenied = !permissionGranted

        if (isReturningFromSettings) {
            popUpRejectCameraDialog.dismiss()
            isReturningFromSettings = false
        }
    }

    private fun showRejectCameraPopUp() {
        if (!popUpRejectCameraDialog.isShowing) {
            popUpRejectCameraDialog.show()
        }
    }


    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }
}