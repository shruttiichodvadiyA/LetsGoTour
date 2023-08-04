package com.example.letsgotour


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.letsgotour.databinding.ActivityMainBinding
import com.example.letsgotour.fragment.*
import com.example.letsgotour.modal.UserSignUp
import com.example.letsgotour.verification.LogIn
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference
    lateinit var headerName: TextView
    lateinit var navigationView: NavigationView
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference
        backGroundLayout()
        navigationView = findViewById(R.id.nav_drawer)
        navigationView.setNavigationItemSelectedListener(this)

        visibilityOfAddItem()
        clickEvent()
        var headerView = binding.navDrawer.getHeaderView(0)
        headerName = headerView.findViewById(R.id.imgProfileName)

    }

    private fun clickEvent() {
        binding.Profile.setOnClickListener {
            binding.txtTitle.text = "Profile"
            var fm = ProfileFragment()
            loadFragment(fm)
        }
        binding.bottomHome.setOnClickListener {
            binding.txtTitle.text = "DashBoard"
            var fm = DashBoardFragment()
            loadFragment(fm)
        }
        binding.bottomPackage.setOnClickListener {
            binding.txtTitle.text = "Package"
            var fm = DisplayItemFragment()
            loadFragment(fm)
        }
        binding.bottomMap.setOnClickListener {
            binding.txtTitle.text = "Map"
            var intent = Intent(this, MapsPlaceDrawActivity::class.java)
            startActivity(intent)
        }
        binding.bottomFavorite.setOnClickListener {
            binding.txtTitle.text = "FavoritePackage"
            var fm = FavoriteFragment()
            loadFragment(fm)
        }
    }

    private fun visibilityOfAddItem() {
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {
                    val Item = postSnapshot.getValue(UserSignUp::class.java)
//                    if (Item != null) {
//                        binding.DisplayProgressBar.visibility= View.GONE
//                    }

                    navigationView.menu.findItem(R.id.AddPackage).isVisible = false
                    Log.d("TAG", "onDataChange==: " + mAuth.currentUser?.email)
                    Log.d("TAG", "onDataChange==mu: " + mAuth.currentUser?.uid)
                    Log.d("TAG", "onDataChange==u: " + Item?.uid)
                    if (mAuth.currentUser?.uid == Item?.uid) {
                        ini(Item?.name)
                    }
                    if (mAuth.currentUser?.email == "shubhamsavaliya0112@gmail.com") {
                        navigationView.menu.findItem(R.id.AddPackage).isVisible = true
                    }

                }

            }


            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun ini(name: String?) {
        headerName.text = name
    }

    private fun backGroundLayout() {

        val versionCode = BuildConfig.VERSION_CODE
        binding.txtVersion.text = "Version $versionCode"
        binding.openDrawer.setOnClickListener {
            binding.Drawer.openDrawer(GravityCompat.START)
        }
        binding.txtTitle.text = "DashBoard"
        var fm = DashBoardFragment()
        loadFragment(fm)

    }

    private fun loadFragment(fm: Fragment) {
        val fragment : androidx.fragment.app.FragmentManager = supportFragmentManager
        val fragmentManager: androidx.fragment.app.FragmentTransaction = fragment.beginTransaction()
        binding.Drawer.closeDrawer(GravityCompat.START)
        fragmentManager.replace(R.id.container, fm, "Hello")
        fragmentManager.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val item = item.itemId

        if (item == R.id.AddPackage) {
            binding.txtTitle.text = "Add Package Of Tour"
            var fm = MainFregment()
            loadFragment(fm)
        }
        if (item == R.id.logOut) {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.logout_item)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)

            var btnNo = dialog.findViewById<AppCompatButton>(R.id.btnNotLogOut)
            var btnYes = dialog.findViewById<AppCompatButton>(R.id.btnConfirmLogOut)

            btnNo.setOnClickListener {
                dialog.dismiss()
            }
            btnYes.setOnClickListener {
                val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                var email = sh?.getString("user", null)
                Log.d("TAG", "onNavigationItemSelected--:$email ")
                mAuth.signOut()
                var intent = Intent(this, LogIn::class.java)
                intent.putExtra("Email", email)
                startActivity(intent)

                finish()
            }
            dialog.show()
        }
        if (item == R.id.DisplayItem) {
            binding.txtTitle.text = "Package Of Tour"
            var fm = DisplayItemFragment()
            loadFragment(fm)
        }
        if (item == R.id.OpenMap) {
            binding.txtTitle.text = "Map"
            var intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        if (item == R.id.dashboard) {
            binding.txtTitle.text = "DashBoard"
            var fm = DashBoardFragment()
            loadFragment(fm)
        }
        if (item == R.id.changePassword){
            binding.txtTitle.text = "ChangePassword"
            var fm = ChangePasswordFragment()
            loadFragment(fm)
        }

        return true
    }
}