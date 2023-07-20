package com.test.mini01_lbs01

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.test.mini01_lbs01.databinding.ActivityMainBinding
import com.test.mini01_lbs01.BuildConfig
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val dialogData = arrayOf(
        "accounting", "airport", "amusement_park",
        "aquarium", "art_gallery", "atm", "bakery",
        "bank", "bar", "beauty_salon", "bicycle_store",
        "book_store", "bowling_alley", "bus_station",
        "cafe", "campground", "car_dealer", "car_rental",
        "car_repair", "car_wash", "casino", "cemetery",
        "church", "city_hall", "clothing_store", "convenience_store",
        "courthouse", "dentist", "department_store", "doctor",
        "drugstore", "electrician", "electronics_store", "embassy",
        "fire_station", "florist", "funeral_home", "furniture_store",
        "gas_station", "gym", "hair_care", "hardware_store", "hindu_temple",
        "home_goods_store", "hospital", "insurance_agency",
        "jewelry_store", "laundry", "lawyer", "library", "light_rail_station",
        "liquor_store", "local_government_office", "locksmith", "lodging",
        "meal_delivery", "meal_takeaway", "mosque", "movie_rental", "movie_theater",
        "moving_company", "museum", "night_club", "painter", "park", "parking",
        "pet_store", "pharmacy", "physiotherapist", "plumber", "police", "post_office",
        "primary_school", "real_estate_agency", "restaurant", "roofing_contractor",
        "rv_park", "school", "secondary_school", "shoe_store", "shopping_mall",
        "spa", "stadium", "storage", "store", "subway_station", "supermarket",
        "synagogue", "taxi_stand", "tourist_attraction", "train_station",
        "transit_station", "travel_agency", "university", "eterinary_care","zoo"
    )

    // 위치 측정 리스너
    var myLocationListener:LocationListener? = null

    // 구글 지도 객체를 담을 변수
    lateinit var mainGoogleMap:GoogleMap

    // 현재 사용자 위치에 표시되는 마커
    var myMarker: Marker? = null

    val placeMarkerList = mutableListOf<Marker>()

    var currentLatitude = 0.0
    var currentLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        requestPermissions(permissionList, 0)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        activityMainBinding.run {

            toolbar.run {
                inflateMenu(R.menu.menu)
                setOnMenuItemClickListener {
                    when(it?.itemId){
                        R.id.itemCurrentLocation -> {
                            getMyLocation()
                        }
                        R.id.itemChoicePlace -> {
                            val adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, dialogData)

                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("리스트 다이얼로그")
                            builder.setIcon(R.mipmap.ic_launcher)

                            builder.setAdapter(adapter){ dialogInterface: DialogInterface, i: Int ->
                                getPlaces(dialogData[i])
                            }

                            builder.setNegativeButton("취소", null)
                            builder.show()
                        }
                    }
                    false
                }
            }
        }

        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        // 구글 지도 사용 준비가 완료되면 반응하는 리스너를 등록한다.
        supportMapFragment. getMapAsync{
            // Toast.makeText(this, "구글 지도가 준비되었습니다", Toast.LENGTH_SHORT).show()

            // 구글맵 객체를 변수에 담아준다.
            mainGoogleMap = it

            // 지도 옵션 설정
            it.uiSettings.isZoomControlsEnabled = true

            // 현재 위치 표시
            it.isMyLocationEnabled = true

            // 현재 위치 표시 버튼
            it.uiSettings.isMyLocationButtonEnabled = false

            // 위치 정보를 관리하는 객체를 가지고 온다.
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

            // 권한 확인
            val a1 = ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
            val a2 = ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
            if(a1 == PackageManager.PERMISSION_GRANTED && a2 == PackageManager.PERMISSION_GRANTED){
                // 현재 저장되어 있는 위치 정보값을 가지고온다.
                val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                // 현재 위치를 표시한다.
                if(location1 != null){
                    setMyLocation(location1)
                } else if(location2 != null){
                    setMyLocation(location2)
                }

                // 현재 위치를 측정하여 지도를 갱신한다
                getMyLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.uiSettings.isZoomControlsEnabled = true

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {

                val current = LatLng(it.latitude, it.longitude)

                googleMap.addMarker(
                    MarkerOptions()
                        .position(current)
                        .title("현재 위치")
                )

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15.0F))
            }
        }
    }

    fun setMyLocation(location: Location){
        // 위치 측정을 중단한다.
        if(myLocationListener != null) {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.removeUpdates(myLocationListener!!)
            myLocationListener = null
        }

        currentLatitude = location.latitude
        currentLongitude = location.longitude

        // 위도와 경도를 관리하는 객체를 생성한다.
        val latLng = LatLng(currentLatitude, currentLongitude)

        // 지도를 이용시키기 위한 객체를 생성한다.
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        // 지도를 이동한다.
        mainGoogleMap.animateCamera(cameraUpdate)

        // 현재 위치에 마커를 표시한다.
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)

        // 기존에 표시한 마커를 제거한다.
        if(myMarker != null){
            myMarker?.remove()
            myMarker = null
        }
        myMarker = mainGoogleMap.addMarker(markerOptions)
    }

    fun getMyLocation() {
        val a1 = ActivityCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val a2 = ActivityCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (a1 == PackageManager.PERMISSION_GRANTED && a2 == PackageManager.PERMISSION_GRANTED) {

            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

            // 위치 측정 리스너
            myLocationListener = object : LocationListener {
                override fun onLocationChanged(p0: Location) {
                    setMyLocation(p0)
                }
            }

            // 위치 측정 요청
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0, 0f, myLocationListener!!
                )
            }
        }
    }

    fun getPlaces(data:String){
        var serverAddress = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${currentLatitude}%2C${currentLongitude}&radius=50000&keyword=${data}&language=ko&key=${BuildConfig.PLACES_API_KEY}"

        if (placeMarkerList.size != 0){
            for (i in 0 until placeMarkerList.size){
                placeMarkerList[i].remove()
            }
        }

        thread {
            val url = URL(serverAddress)

            // 접속 후 스트림 추출
            val httpURLConnection = url.openConnection() as HttpURLConnection

            val inputStreamReader = InputStreamReader(httpURLConnection.inputStream, "UTF-8")
            val bufferedReader = BufferedReader(inputStreamReader)

            var str: String? = null
            val stringBuffer = StringBuffer()
            // 문서의 마지막까지 읽어온다.
            do {
                str = bufferedReader.readLine()
                if (str != null) {
                    stringBuffer.append(str)
                }
            } while (str != null)

            val data = stringBuffer.toString()

            // 최 상위가 { } 이므로 JSONObject를 생성한다.
            val root = JSONObject(data)

            // resultsArray
            val resultsArray = root.getJSONArray("results")

            for (idx in 0 until resultsArray.length()) {
                // idx 번째 JSONObject를 추출한다.
                val resultObject = resultsArray.getJSONObject(idx)
                // geometry 추출
                val geometry = resultObject.getJSONObject("geometry")
                // location 추출
                val location = geometry.getJSONObject("location")
                // 좌표 추출
                val lat = location.getDouble("lat")
                val lng = location.getDouble("lng")
                Log.d("qwerty", "$lat")
                // name 추출
                val name = resultObject.getString("name")

                val vicinity = resultObject.getString("vicinity")

                val latLng = LatLng(lat, lng)
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title(name)
                markerOptions.snippet(vicinity)

                runOnUiThread {
                    placeMarkerList.add(mainGoogleMap.addMarker(markerOptions)!!)
                }
            }
        }
    }
}