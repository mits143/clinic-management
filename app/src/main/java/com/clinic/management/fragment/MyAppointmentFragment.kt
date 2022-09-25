package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.viewpager.widget.ViewPager
import com.clinic.management.R
import com.clinic.management.adapter.FragmentPagerAdapter
import com.clinic.management.databinding.FragmentMyAppointmentBinding

class MyAppointmentFragment : BaseFragment<FragmentMyAppointmentBinding>(),
    RadioGroup.OnCheckedChangeListener,
    ViewPager.OnPageChangeListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMyAppointmentBinding =
        FragmentMyAppointmentBinding::inflate

    private lateinit var adapter: FragmentPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FragmentPagerAdapter(
            childFragmentManager
        )
        adapter.add(ActiveFragment())
        adapter.add(CompletedFragment())
        adapter.add(CancelledFragment())

        binding.rbTabGroup.setOnCheckedChangeListener(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(this)
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