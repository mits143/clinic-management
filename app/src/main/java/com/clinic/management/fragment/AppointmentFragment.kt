package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.AfternoonAdapter
import com.clinic.management.adapter.CalendarAdapter
import com.clinic.management.adapter.EveningAdapter
import com.clinic.management.adapter.MorningAdapter
import com.clinic.management.databinding.FragmentAppointmentBinding
import com.clinic.management.model.CalendarDateModel
import com.clinic.management.model.appointmentslots.Afternoon
import com.clinic.management.model.appointmentslots.Evening
import com.clinic.management.model.appointmentslots.Morning
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.BookAppointmentViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class AppointmentFragment : BaseFragment<FragmentAppointmentBinding>(), MorningAdapter.OnClick,
    AfternoonAdapter.OnClick, EveningAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAppointmentBinding =
        FragmentAppointmentBinding::inflate

    private val viewModel: BookAppointmentViewModel by viewModel()

    private val args: AppointmentFragmentArgs by navArgs()

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private lateinit var morningAdapter: MorningAdapter
    private lateinit var afternoonAdapter: AfternoonAdapter
    private lateinit var eveningAdapter: EveningAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()

    private var date = ""
    private var time = ""

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        setUpAdapter()
        setUpCalendar()
        binding.txtResult.text = args.doctorName
        binding.txtLocation.setText(args.doctorSpecialist)
        binding.txtspecialist.setText(args.doctorSpecialist1)
        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.ivCalendarPrevious.setOnClickListener {
            var spf = SimpleDateFormat("EEE MMM dd hh:mm:ss zzzz yyyy")
            val newDate = spf.parse(cal.time.toString())
            spf = SimpleDateFormat("dd MMM yyyy")
            val date = spf.format(newDate)
            val strDate: Date = spf.parse(date)
            val your_date_is_outdated = System.currentTimeMillis() >= strDate.time
            if (!your_date_is_outdated) {
                cal.add(Calendar.MONTH, -1)
                setUpCalendar()
            }
        }

        binding.btnConfirm.setOnClickListener {
            if (date.isNotEmpty() && time.isNotEmpty()) {
                if (args.appointmentId == "0") {
                    viewModel.fetchBookAppointmentData(
                        "Bearer " + prefs.accessToken, args.doctorId, date, time
                    )
                } else {
                    viewModel.fetchRescheduleAppointmentData(
                        "Bearer " + prefs.accessToken, args.appointmentId, date, time
                    )
                }
            } else {
                showToast("Select date & time to book your appointment")
            }
        }
        hud = KProgressHUD.create(requireContext()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        morningTimes(arrayListOf())
        afternoonTimes(arrayListOf())
        eveningTimes(arrayListOf())
    }

    private fun setUpAdapter() {
        adapter = CalendarAdapter { data: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            var spf = SimpleDateFormat("EEE MMM dd hh:mm:ss zzzz yyyy")
            val newDate = spf.parse(data.date.toString())
            spf = SimpleDateFormat("yyyy-MM-dd")
            date = spf.format(newDate)
            viewModel.fetchAppointmentSlotsData(
                "Bearer " + prefs.accessToken, args.doctorId, date
            )
            adapter.setData(calendarList2)
        }
        binding.rvCalender.adapter = adapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        binding.tvDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarDateModel(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        adapter.setData(calendarList)
    }

    private fun setObserver() {
        viewModel.getAppointmentSlotsData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        it.data?.let {
                            showProgress(false)
                            morningTimes(it.data.morning)
                            afternoonTimes(it.data.afternoon)
                            eveningTimes(it.data.evening)
                        }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        showToast(it.message!!)
                        if (it.message == "Invalid authentication.") {
                            requireActivity().startActivity(
                                Intent(
                                    requireContext(), LoginActivity::class.java
                                )
                            )
                            requireActivity().finish()
                            prefs.accessToken = ""
                        }
                    }
                }
            }
        }
        viewModel.getBookAppointmentData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            if (it["status"].asBoolean) {
                                val action =
                                    AppointmentFragmentDirections.actionNavAppointmentConfirmed(
                                        date,
                                        time,
                                        it["doc_name"].asString,
                                        args.doctorSpecialist,
                                        args.doctorSpecialist1,
                                        it["doc_address"].asString
                                    )
                                findNavController().navigate(action)
                            } else showToast(it["message"].asString)
                        }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        showToast(it.message!!)
                        if (it.message == "Invalid authentication.") {
                            requireActivity().startActivity(
                                Intent(
                                    requireContext(), LoginActivity::class.java
                                )
                            )
                            requireActivity().finish()
                            prefs.accessToken = ""
                        }
                    }
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) hud.show()
        else if (hud.isShowing) hud.dismiss()
    }

    private fun morningTimes(dataList: ArrayList<Morning>) {
        if (dataList.isNotEmpty()) {
            binding.clMorningTime.visibility = View.VISIBLE
            morningAdapter = MorningAdapter(dataList, this)
            binding.rvMorning.adapter = morningAdapter
        } else {
            binding.clMorningTime.visibility = View.GONE
        }
    }

    private fun afternoonTimes(dataList: ArrayList<Afternoon>) {
        if (dataList.isNotEmpty()) {
            binding.clAfternoonTime.visibility = View.VISIBLE
            afternoonAdapter = AfternoonAdapter(dataList, this)
            binding.rvAfternoon.adapter = afternoonAdapter
        } else {
            binding.clAfternoonTime.visibility = View.GONE
        }
    }

    private fun eveningTimes(dataList: ArrayList<Evening>) {
        if (dataList.isNotEmpty()) {
            binding.clEveningTime.visibility = View.VISIBLE
            eveningAdapter = EveningAdapter(dataList, this)
            binding.rvEvening.adapter = eveningAdapter
        } else {
            binding.clEveningTime.visibility = View.GONE
        }
    }

    override fun itemClick(data: Morning) {
        morningAdapter.clearSelectedItems()
        afternoonAdapter.clearSelectedItems()
        eveningAdapter.clearSelectedItems()
        time = data.time
        data.isChecked = true
        morningAdapter.notifyDataSetChanged()
    }

    override fun itemClick(data: Afternoon) {
        morningAdapter.clearSelectedItems()
        afternoonAdapter.clearSelectedItems()
        eveningAdapter.clearSelectedItems()
        time = data.time
        data.isChecked = true
        afternoonAdapter.notifyDataSetChanged()
    }

    override fun itemClick(data: Evening) {
        morningAdapter.clearSelectedItems()
        afternoonAdapter.clearSelectedItems()
        eveningAdapter.clearSelectedItems()
        time = data.time
        data.isChecked = true
        eveningAdapter.notifyDataSetChanged()
    }
}