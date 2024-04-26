package utfpr.edu.pb.leituralivroapp

import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import utfpr.edu.pb.leituralivroapp.databinding.ActivityMain2Binding

import android.Manifest
import android.content.Intent

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
            setSupportActionBar(Toolbar(this))
            val navView: BottomNavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_activity_main2)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_book
                )
            )
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    this.startActivity(intent)
                    true
                }
                else -> false
            }
        }
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
    }


}