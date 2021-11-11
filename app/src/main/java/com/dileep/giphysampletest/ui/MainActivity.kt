package com.dileep.giphysampletest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dileep.giphysampletest.R
import com.dileep.giphysampletest.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var trendingFragment: TrendingFragment
    lateinit var favoriteFragment: FavoritesFragment
    lateinit var tabSelectedListener: TabLayout.OnTabSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trendingFragment = TrendingFragment.newInstance()
        favoriteFragment = FavoritesFragment.newInstance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, trendingFragment)
                .commitNow()
        }
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        setTabLayout()
    }

    private fun setTabLayout(){
        tabSelectedListener = object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, if (tab?.position == 0) trendingFragment else favoriteFragment)
                    .commitNow()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        }
        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.tabLayout.removeOnTabSelectedListener(tabSelectedListener)
    }

    companion object{
        val TAG = MainActivity::class.java.simpleName
    }
}