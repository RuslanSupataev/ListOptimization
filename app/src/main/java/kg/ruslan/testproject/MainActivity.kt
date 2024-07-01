package kg.ruslan.testproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kg.ruslan.testproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	
	private lateinit var navController: NavController
	private lateinit var binding: ActivityMainBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		navController = findNavController(R.id.nav_host_fragment_activity_main)
		val appBarConfiguration = AppBarConfiguration(navController.graph)
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
	}
	
	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}
}