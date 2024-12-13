package com.example.allergifyapp.ui.camera

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityCameraBinding
import com.example.allergifyapp.ui.main.AllergyDetailScreen
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.utils.DataStatus
import com.example.allergifyapp.viewmodel.AllergyScanViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import java.nio.ByteBuffer

@AndroidEntryPoint
class CameraActivity : BaseActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var requestCodeCamera = 100
    private lateinit var scannerLauncher: ActivityResultLauncher<ScanOptions>
    private lateinit var imageCapture: ImageCapture
    private val allergyScanViewModel: AllergyScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupScannerLauncher()
        setupCheckSelfPermission()
        setupOcrToggleButton()
        setupBarcodeScanButton()
        setupCameraCaptureButton()
        setupImageCapture()
        setupObserveAllergyScanResult()

    }

    private fun setupImageCapture() {
        imageCapture = ImageCapture.Builder().build()
    }

    private fun setupCameraCaptureButton() {
        binding.cameraCaptureButton.setOnClickListener { // Add click listener for capture button
            captureImage()
        }
    }

    private fun setupObserveAllergyScanResult() {
        allergyScanViewModel.allergyScanResult.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {

                }

                DataStatus.Status.SUCCESS -> {
                    val intent = Intent(this, AllergyDetailScreen::class.java)
                    startActivity(intent)
                }

                DataStatus.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    private fun setupCheckSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), requestCodeCamera)
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCamera) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startCamera()
            } else {
                Toast.makeText(this, "Izin kamera diperlukan untuk menggunakan fitur ini.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .apply {
                    surfaceProvider = binding.viewFinder.surfaceProvider
                }

            imageCapture = ImageCapture.Builder().build() // Initialize ImageCapture here

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                // Bind the camera to the lifecycle
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture // Bind ImageCapture here
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun setupOcrToggleButton() {
        binding.ocrToggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.floatingInformationTextView.text = getString(R.string.activity_camera_screen_capture_button_ocr_mode_information_textview)
                binding.floatingInformationImageView.setImageResource(R.drawable.document_scanner_24px)
            } else {
                binding.floatingInformationTextView.text = getString(R.string.activity_camera_screen_capture_button_information_textview)
                binding.floatingInformationImageView.setImageResource(R.drawable.photo_camera_24px)
            }
        }
    }

    private fun setupBarcodeScanButton() {
        binding.barcodeScanButton.setOnClickListener {
            startBarcodeScanning()
        }
    }

    private fun startBarcodeScanning() {
        scannerLauncher.launch(
            ScanOptions().setPrompt("")
                .setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        )
    }

    private fun setupScannerLauncher() {
        scannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
            ScanContract()
        ) { result ->
            if (result.contents == null) {
                Toast.makeText(this, "Dibatalkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Hasil: ${result.contents}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun captureImage() {
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    // Convert ImageProxy to ByteArray
                    val byteArray = imageToByteArray(image)
                    // Send the byte array to the ViewModel
                    allergyScanViewModel.uploadImage(byteArray) // Assuming you have a method in your ViewModel
                    image.close() // Close the image to free up resources
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Image capture failed: ${exception.message}", exception)
                    Toast.makeText(this@CameraActivity, "Image capture failed", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun imageToByteArray(image: ImageProxy): ByteArray {
        val buffer: ByteBuffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return bytes
    }

}