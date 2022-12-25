package com.clinic.management.activities

import android.Manifest
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import com.clinic.management.prefs
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(),
    CoroutineScope by CoroutineScope(
        Dispatchers.Main
    ) {

    protected lateinit var binding: B
        private set

    abstract val bindingInflater: (LayoutInflater) -> B

    private lateinit var dexter: DexterBuilder

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var googleApiClient: GoogleApiClient? = null

    val REQUEST_LOCATION = 199

    private lateinit var manager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater).apply {
            setContentView(root)
        }
        onViewBindingCreated(savedInstanceState)
    }

    open fun onViewBindingCreated(savedInstanceState: Bundle?) {

    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(msg: Int) {
        Toast.makeText(this, getString(msg), Toast.LENGTH_SHORT).show()
    }


    fun getPermission() {
        manager = getSystemService(LOCATION_SERVICE) as LocationManager
        dexter = Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    report.let {
                        if (report.areAllPermissionsGranted()) {
                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                enableLoc()
                            } else {
                                getLastKnownLocation()
                            }
                        } else if (report.isAnyPermissionPermanentlyDenied) {
                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                enableLoc()
                            } else {
                                AlertDialog.Builder(this@BaseActivity)
                                    .apply {
                                        setMessage("please allow the required permissions")
                                            .setCancelable(false)
                                            .setPositiveButton("Settings") { _, _ ->
                                                val reqIntent =
                                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                                        .apply {
                                                            val uri = Uri.fromParts(
                                                                "package",
                                                                packageName,
                                                                null
                                                            )
                                                            data = uri
                                                        }
                                                resultLauncher.launch(reqIntent)
                                            }
//                                     setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
                                        val alert = this.create()
                                        alert.show()
                                    }
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).withErrorListener {
                showToast(it.name)
            }
        dexter.check()
    }

    fun getLastKnownLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    prefs.latitude = location.latitude.toString()
                    prefs.longitude = location.longitude.toString()
                    prefs.city
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    prefs.city = addresses!![0].locality

                    val intent = Intent("locationSelected")
                    sendBroadcast(intent)
                }

            }

    }

    private fun enableLoc() {
        googleApiClient = GoogleApiClient.Builder(this@BaseActivity)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                override fun onConnected(bundle: Bundle?) {}
                override fun onConnectionSuspended(i: Int) {
                    googleApiClient?.connect()
                }
            })
            .addOnConnectionFailedListener { connectionResult ->
                Log.d(
                    "Location error",
                    "Location error " + connectionResult.errorCode
                )
            }.build()
        googleApiClient!!.connect()
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(30 * 1000)
        locationRequest.setFastestInterval(5 * 1000)
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(
                googleApiClient!!,
                builder.build()
            )
        result.setResultCallback(object : ResultCallback<LocationSettingsResult?> {
            override fun onResult(result: LocationSettingsResult) {
                val status: Status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                        getLastKnownLocation()
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        status.startResolutionForResult(this@BaseActivity, REQUEST_LOCATION)

//                                finish();
                    } catch (e: SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
        })
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
//                val data: Intent? = result.data
                dexter.check()
            }
        }
}