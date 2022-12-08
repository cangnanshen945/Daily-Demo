package com.cangcang.tablayoutwithviewpager2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val tabList = listOf("left", "right")
    private lateinit var layoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewpager2.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return tabList.size
            }

            override fun createFragment(position: Int): Fragment {
                return EmptyFragment().apply {
                    arguments = Bundle().apply { putInt("index", position) }
                }
            }
        }
        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until tab_layout.tabCount) {
                    val tabView = tab_layout.getTabAt(i)?.customView as TextView?
                    if (position == i) {
                        tabView?.textSize = 20f
                    } else {
                        tabView?.textSize = 15f
                    }
                }
            }
        })

        layoutMediator = TabLayoutMediator(
            tab_layout,
            viewpager2,
            false
        ) { tab, position ->
            tab.customView = TextView(this@MainActivity).apply {
                gravity = Gravity.CENTER
                setTextColor(Color.parseColor("#ff0000"))
                textSize = 15f
                text = "$position page"
            }
        }
        layoutMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        layoutMediator.detach()
    }
}