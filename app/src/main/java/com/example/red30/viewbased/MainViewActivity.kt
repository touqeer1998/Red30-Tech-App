package com.example.red30.viewbased

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.red30.databinding.ActivityMainViewBinding

class MainViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainViewBinding

//    private val navController: NavController by lazy {
//        Navigation.findNavController(this, R.id.root_nav_host_fragment)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
