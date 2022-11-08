package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.clinic.management.R
import com.clinic.management.adapter.FragmentPagerAdapter
import com.clinic.management.databinding.FragmentMyResultBinding

class MyResultFragment : BaseFragment<FragmentMyResultBinding>(),
    RadioGroup.OnCheckedChangeListener,
    ViewPager.OnPageChangeListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMyResultBinding =
        FragmentMyResultBinding::inflate

    private lateinit var adapter: FragmentPagerAdapter
    private val args: MyResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        adapter = FragmentPagerAdapter(
            childFragmentManager
        )
        adapter.add(DoctorFragment())
        adapter.add(LabFragment())
        adapter.add(RadiologyFragment())
        binding.viewPager.adapter = adapter
        binding.rbTabGroup.setOnCheckedChangeListener(this)
        binding.viewPager.addOnPageChangeListener(this)
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.currentItem = args.currentItem

    }

    override fun onCheckedChanged(p0: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.rbActive -> binding.viewPager.currentItem = 0
            R.id.rbCompleted -> binding.viewPager.currentItem = 1
            R.id.rbCancelled -> binding.viewPager.currentItem = 2
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> binding.rbActive.isChecked = true
            1 -> binding.rbCompleted.isChecked = true
            2 -> binding.rbCancelled.isChecked = true
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }
}