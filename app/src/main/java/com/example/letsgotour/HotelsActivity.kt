package com.example.letsgotour

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.letsgotour.R
import com.example.letsgotour.databinding.ActivityHotelsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HotelsActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityHotelsBinding
    var hotelGoaList: ArrayList<LatLng> = ArrayList()
    var hotelDubaiList: ArrayList<LatLng> = ArrayList()
    var hotelParisList: ArrayList<LatLng> = ArrayList()
    private var locationManager: LocationManager? = null
    var tajMahalHotelList: ArrayList<LatLng> = ArrayList()
    private var mMap: GoogleMap? = null

    val goaHotel1 = LatLng(15.211429763611369, 73.9475984455389)
    val goaHotel2 = LatLng(15.182936541588978, 73.94553850914338)
    val goaHotel3 = LatLng(15.277678162689325, 73.96201800030764)
    val goaHotel4 = LatLng(15.315430396249159, 73.91257952681485)
    val goaHotel5 = LatLng(15.509382303807183, 73.91532610867554)
    val goaHotel6 = LatLng(15.501107278175514, 73.83285596539851)
    val goaHotel7 = LatLng(15.513562134945811, 73.79799658268371)
    val goaHotel8 = LatLng(15.52677101033462, 73.76587894917118)
    val goaHotel9 = LatLng(15.536372818371419, 73.76389626912692)
    val goaHotel10 = LatLng(15.537342637558826, 73.76248702625185)
    val goaHotel11 = LatLng(15.540833948841888, 73.76215149223397)
    val goaHotel12 = LatLng(15.546200108237775, 73.76490287118054)
    val goaHotel13 = LatLng(15.560681575417489, 73.75356182137642)
    val goaHotel14 = LatLng(15.566241153715007, 73.75034069480483)

    val dubaiHotel1 = LatLng(25.100630767165985, 55.17849790669314)
    val dubaiHotel2 = LatLng(25.102391461989615, 55.17136872941345)
    val dubaiHotel3 = LatLng(25.119410204953898, 55.19178410071438)
    val dubaiHotel4 = LatLng(25.121757431829348, 55.20085759907034)
    val dubaiHotel5 = LatLng(25.11852998325207, 55.201505706095766)
    val dubaiHotel6 = LatLng(25.137306670920044, 55.254002375155274)
    val dubaiHotel7 = LatLng(25.16194669212778, 55.26923289025279)
    val dubaiHotel8 = LatLng(25.20417514806851, 55.27409369294348)
    val dubaiHotel9 = LatLng(25.201536298389236, 55.27992665617232)
    val dubaiHotel10 = LatLng(25.24677441263399, 55.30014638161423)
    val dubaiHotel11 = LatLng(25.26167885517945, 55.28881673134075)
    val dubaiHotel12 = LatLng(25.26664659622032, 55.32246235942565)
    val dubaiHotel13 = LatLng(25.258573913810878, 55.305296222647634)
    val dubaiHotel14 = LatLng(25.232489272688905, 55.31971577754117)
    val dubaiHotel15 = LatLng(25.252053278530926, 55.33962849620366)

    val tajMahalHotel = LatLng(27.17327397597382, 78.03986505022733)
    val tajMahalHotel1 = LatLng(27.17096328719824, 78.03974698959864)
    val tajMahalHotel2 = LatLng(27.17040311301865, 78.04037664628493)
    val tajMahalHotel3 = LatLng(27.16994796942786, 78.04059309077083)
    val tajMahalHotel4 = LatLng(27.16921273355334, 78.04053406045648)
    val tajMahalHotel5 = LatLng(27.168582527521004, 78.04069147462806)
    val tajMahalHotel6 = LatLng(27.169982980539178, 78.04222626280084)
    val tajMahalHotel7 = LatLng(27.169457812716228, 78.04230496988663)
    val tajMahalHotel8 = LatLng(27.169370284505543, 78.04200981831494)
    val tajMahalHotel9 = LatLng(27.169282756226213, 78.04346589940194)
    val tajMahalHotel10 = LatLng(27.17040311301865, 78.04368234388785)
    val tajMahalHotel11 = LatLng(27.171190857155707, 78.04474488954594)
    val tajMahalHotel12 = LatLng(27.17124337323384, 78.0449023037175)
    val tajMahalHotel13 = LatLng(27.1708582547537, 78.04626000094729)
    val tajMahalHotel14 = LatLng(27.169972148712645, 78.04896704904536)
    val tajMahalHotel15 = LatLng(27.168376234066415, 78.04891099156421)

    val hotelParis1 = LatLng(48.863162011241705, 2.292237280385205)
    val hotelParis2 = LatLng(48.868356479516024, 2.288804052897658)
    val hotelParis3 = LatLng(48.877163258670436, 2.2925806031339597)
    val hotelParis4 = LatLng(48.88551697511674, 2.295670507872752)
    val hotelParis5 = LatLng(48.87671166668147, 2.3076868040791667)
    val hotelParis6 = LatLng(48.876485869158145, 2.3114633543154683)
    val hotelParis7 = LatLng(48.87151806578314, 2.331376073743241)
    val hotelParis8 = LatLng(48.876485869158145, 2.339615819713354)
    val hotelParis9 = LatLng(48.88777449681169, 2.334465978482033)
    val hotelParis10 = LatLng(48.885065458547764, 2.3543786979098056)
    val hotelParis11 = LatLng(48.89454645040687, 2.358841893643617)
    val hotelParis12 = LatLng(48.85932227509452, 2.3581552481461068)
    val hotelParis13 = LatLng(48.860451639834054, 2.3701715443525218)
    val hotelParis14 = LatLng(48.876485869158145, 2.3540353751610508)
    val hotelParis15 = LatLng(48.85683758296947, 2.336525914974561)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.LoadMap) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initView()
        mMap!!.setOnMapClickListener {

        }
    }

    private fun initView() {
        hotelGoaList.add(goaHotel1)
        hotelGoaList.add(goaHotel2)
        hotelGoaList.add(goaHotel3)
        hotelGoaList.add(goaHotel4)
        hotelGoaList.add(goaHotel5)
        hotelGoaList.add(goaHotel6)
        hotelGoaList.add(goaHotel7)
        hotelGoaList.add(goaHotel8)
        hotelGoaList.add(goaHotel9)
        hotelGoaList.add(goaHotel10)
        hotelGoaList.add(goaHotel11)
        hotelGoaList.add(goaHotel12)
        hotelGoaList.add(goaHotel13)
        hotelGoaList.add(goaHotel14)


        hotelDubaiList.add(dubaiHotel1)
        hotelDubaiList.add(dubaiHotel2)
        hotelDubaiList.add(dubaiHotel3)
        hotelDubaiList.add(dubaiHotel4)
        hotelDubaiList.add(dubaiHotel5)
        hotelDubaiList.add(dubaiHotel6)
        hotelDubaiList.add(dubaiHotel7)
        hotelDubaiList.add(dubaiHotel8)
        hotelDubaiList.add(dubaiHotel9)
        hotelDubaiList.add(dubaiHotel10)
        hotelDubaiList.add(dubaiHotel11)
        hotelDubaiList.add(dubaiHotel12)
        hotelDubaiList.add(dubaiHotel13)
        hotelDubaiList.add(dubaiHotel14)
        hotelDubaiList.add(dubaiHotel15)

        tajMahalHotelList.add(tajMahalHotel)
        tajMahalHotelList.add(tajMahalHotel1)
        tajMahalHotelList.add(tajMahalHotel2)
        tajMahalHotelList.add(tajMahalHotel3)
        tajMahalHotelList.add(tajMahalHotel4)
        tajMahalHotelList.add(tajMahalHotel5)
        tajMahalHotelList.add(tajMahalHotel6)
        tajMahalHotelList.add(tajMahalHotel7)
        tajMahalHotelList.add(tajMahalHotel8)
        tajMahalHotelList.add(tajMahalHotel9)
        tajMahalHotelList.add(tajMahalHotel10)
        tajMahalHotelList.add(tajMahalHotel11)
        tajMahalHotelList.add(tajMahalHotel12)
        tajMahalHotelList.add(tajMahalHotel13)
        tajMahalHotelList.add(tajMahalHotel14)
        tajMahalHotelList.add(tajMahalHotel15)

        hotelParisList.add(hotelParis1)
        hotelParisList.add(hotelParis2)
        hotelParisList.add(hotelParis3)
        hotelParisList.add(hotelParis4)
        hotelParisList.add(hotelParis5)
        hotelParisList.add(hotelParis6)
        hotelParisList.add(hotelParis7)
        hotelParisList.add(hotelParis8)
        hotelParisList.add(hotelParis9)
        hotelParisList.add(hotelParis10)
        hotelParisList.add(hotelParis11)
        hotelParisList.add(hotelParis12)
        hotelParisList.add(hotelParis13)
        hotelParisList.add(hotelParis14)
        hotelParisList.add(hotelParis15)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        for (i in 0 until 9) {
            mMap!!.addMarker(MarkerOptions().position(hotelParisList[i]).title(""))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(hotelParisList[i]))
            drawMarker(hotelParisList[i], false)
        }

    }

//    private val locationListener = object : LocationListener {
//        override fun onLocationChanged(location: Location) {
//            var latLng = LatLng(location.getLatitude(), location.getLongitude());
//            drawMarker(latLng, false);
//            locationManager!!.removeUpdates(locationListener);
//        }
//    }

    private fun drawMarker(latLng: LatLng, flag: Boolean) {
        if (mMap != null) {
//            mMap!!.clear()
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            //            markerOptions.title(title);
            if (flag) {
//                markerOptions.icon(BitmapFromVector(this, R.drawable.ic_map_event));
                markerOptions.icon(BitmapFromVector(this@HotelsActivity, R.drawable.bottomtoolmap))

            } else {
                Log.e("TAG", "drawMarker: mark")
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            }
            mMap!!.addMarker(markerOptions)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f));
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
        }
    }


    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )


        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}