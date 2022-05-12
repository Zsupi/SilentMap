package hu.nemeth.android.silentmap.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import hu.nemeth.android.silentmap.databinding.FragmentViewPagerBinding
import hu.nemeth.android.silentmap.view.animations.DepthPageTransformer
import hu.nemeth.android.silentmap.view.callback.PageChangeCallback
import hu.bme.aut.silentmap2_1_0.view.fragment.ListFragment
import hu.bme.aut.silentmap2_1_0.view.fragment.SettingsFragment
import hu.nemeth.android.silentmap.view.fragment.MapsFragment
import hu.nemeth.android.silentmap.view.fragment.SharedPrefFragment
import permissions.dispatcher.*

class ViewPagerFragment : Fragment(), PageChangeCallback {

    private lateinit var binding: FragmentViewPagerBinding

    private val listFragment = ListFragment()
    private val mapsFragment = MapsFragment()
    private val settingsFragment = SettingsFragment()

    private val fragmentList = arrayListOf(
        listFragment,
        mapsFragment,
        settingsFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )
        binding.viewPager.setPageTransformer(DepthPageTransformer())
        binding.viewPager.adapter = adapter

        binding.bottomNavigation.addBubbleListener {
            when (it) {
                binding.bottomNavigation[0].id -> changePage(0)
                binding.bottomNavigation[1].id -> changePage(1)
                binding.bottomNavigation[2].id -> changePage(2)
                else -> return@addBubbleListener
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigation.setSelected(0)
                    1 -> binding.bottomNavigation.setSelected(1)
                    2 -> binding.bottomNavigation.setSelected(2)
                }
            }
        })
    }

    override fun changePage(page: Int) {
        binding.viewPager.isUserInputEnabled = page != fragmentList.indexOf(mapsFragment)
        binding.viewPager.setCurrentItem(page, true)
    }



}
