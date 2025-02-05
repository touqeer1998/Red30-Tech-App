package com.example.red30.viewbased

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.red30.R
import com.example.red30.databinding.ActivityMainViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainViewActivity : AppCompatActivity() {
    private val viewModel: MainViewViewModel by viewModel()

    private lateinit var binding: ActivityMainViewBinding

    val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpUI()
    }

    private fun setUpUI() {
        setUpWindowInsets()

        NavigationUI.setupWithNavController(
            binding.navigationView as NavigationBarView,
            navController
        )

        (binding.navigationView as NavigationBarView).setOnItemReselectedListener {
            viewModel.activeDestinationClick()
        }
    }

    private fun setUpWindowInsets() {
        if (binding.navigationView is BottomNavigationView) {
            ViewCompat.setOnApplyWindowInsetsListener(binding.mainContainer) { view, windowInsets ->
                val insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            or WindowInsetsCompat.Type.displayCutout()
                )
                view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = insets.top
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                windowInsets
            }
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(binding.rootLayout) { view, windowInsets ->
                val insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            or WindowInsetsCompat.Type.displayCutout()
                )
                view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = insets.top
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }
}
