package hu.nemeth.android.silentmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import com.zeugmasolutions.localehelper.Locales
import hu.bme.aut.silentmap2_1_0.view.viewmodel.MainActivityViewModel
import hu.nemeth.android.silentmap.databinding.ActivityMainBinding
import hu.nemeth.android.silentmap.logic.geofence.GeofenceHelper
import permissions.dispatcher.*
import java.util.*

@RuntimePermissions
class MainActivity : LocaleAwareCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var viewModel: MainActivityViewModel
    private val TAG = "MainActivity"

    override fun updateLocale(locale: Locale) {
        super.updateLocale(locale)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNightMode()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        geofenceHelper = GeofenceHelper(this)
        geofencingClient = LocationServices.getGeofencingClient(this)

        viewModel.territories.observe(this, Observer {
            createGeofenceWithPermissionCheck()
        })
    }

    private fun setNightMode() {
        val pref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val nightMode = pref.getBoolean("NightMode", false)
        if (nightMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.READ_PHONE_STATE
    )
    fun createGeofence(){
        if (viewModel.territories.value?.isEmpty() == false){
            geofenceHelper.generateGeofences(viewModel.territories.value)
            val geofenceRequest = geofenceHelper.getGeofencingRequest()
            val pendingIntent = geofenceHelper.getPendingIntent()

            geofencingClient.addGeofences(geofenceRequest, pendingIntent)
        }
    }


    @OnPermissionDenied(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.READ_PHONE_STATE
    )
    fun locationPermissionDenied() {
        Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show()
    }


    @OnShowRationale(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.READ_PHONE_STATE
    )
    fun showRationaleForLocation(request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(getString(R.string.permission_longdescription))
            .setCancelable(false)
            .setPositiveButton("Proceed") { _, _ -> request.proceed() }
            .setNegativeButton("Exit") { _, _ -> request.cancel() }
            .create()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }


}

/*todo: szebb settings
 *      nezni, hogy a telefon nemitott e mielott lenemitjuk, ezt az allapotot el kene meteni
 *      nezni, hogy megadott e napot
 *      delete nel szepen megkerdezni hogy tenyleg akarja e torolni
 *      ha torlunk egy geofencet akkor toroljuk
 *      alarmnal ugy kesziteni az id-t hogy az 2-sevel ugorjon
 */

/*todo: refaktoralas:
 *          sharedPrefeket egykonstansba szedni mint egy fuggveny
 *          megoldani, hogy ne kelljen kiszedni a main activitybol
 *          settings fragment fuggvenyeit kicsit atrendezni
 */