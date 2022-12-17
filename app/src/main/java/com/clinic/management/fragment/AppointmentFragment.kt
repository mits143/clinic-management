package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.clinic.management.R
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.CalendarAdapter
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


class AppointmentFragment : BaseFragment<FragmentAppointmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAppointmentBinding =
        FragmentAppointmentBinding::inflate

    private val viewModel: BookAppointmentViewModel by viewModel()

    private val args: AppointmentFragmentArgs by navArgs()

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()

    private var date = ""
    private var time = ""
    private var isTimeSelected = false

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        setUpAdapter()
        setUpCalendar()
        binding.txtResult.text = args.doctorName
        binding.txtLocation.setText(args.doctorSpecialist)
        binding.txtspecialist.setText(args.doctorSpecialist1)
        binding.rbMorning.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            if (radioButton != null && radioButton.isChecked) {
                isTimeSelected = true
                time = radioButton.text.toString()
                binding.rbAfternoon.clearCheck()
                binding.rbEvening.clearCheck()
            }
        }
        binding.rbAfternoon.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            if (radioButton != null && radioButton.isChecked) {
                isTimeSelected = true
                time = radioButton.text.toString()
                binding.rbMorning.clearCheck()
                binding.rbEvening.clearCheck()
            }
        }
        binding.rbEvening.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            if (radioButton != null && radioButton.isChecked) {
                isTimeSelected = true
                time = radioButton.text.toString()
                binding.rbMorning.clearCheck()
                binding.rbAfternoon.clearCheck()
            }
        }
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
            if (isTimeSelected) {
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
                showToast("Select date & time to confirm your appointment")
            }
        }
        hud = KProgressHUD.create(requireContext()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun setUpAdapter() {
        adapter = CalendarAdapter { data: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
                isTimeSelected = false
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
                            } else
                                showToast(it["message"].asString)
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
            binding.rbMorning.removeAllViews()
            for (i in 0 until dataList.size) {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
                if (i - 1 != dataList.size) {
                    params.setMargins(0, 0, 15, 0)
                }
                val radioButtonMorning = RadioButton(requireContext())
                radioButtonMorning.layoutParams = params
                radioButtonMorning.setPadding(30, 25, 30, 25)
                radioButtonMorning.buttonDrawable = null
                radioButtonMorning.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.radiobtn_appointment, null
                )
                radioButtonMorning.text = dataList[i].time
                radioButtonMorning.gravity = Gravity.CENTER
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_medium)
                radioButtonMorning.typeface = typeface
                radioButtonMorning.setTextColor(
                    ResourcesCompat.getColor(
                        resources, R.color.grey_dark, null
                    )
                )
                radioButtonMorning.id = System.currentTimeMillis().toInt()
                binding.rbMorning.addView(radioButtonMorning)
            }
        } else {
            binding.clMorningTime.visibility = View.GONE
        }
    }

    private fun afternoonTimes(dataList: ArrayList<Afternoon>) {
        if (dataList.isNotEmpty()) {
            binding.clAfternoonTime.visibility = View.VISIBLE
            binding.rbAfternoon.removeAllViews()
            for (i in 0 until dataList.size) {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
                if (i - 1 != dataList.size) {
                    params.setMargins(0, 0, 15, 0)
                }
                val radioButtonAfternoon = RadioButton(requireContext())
                radioButtonAfternoon.layoutParams = params
                radioButtonAfternoon.setPadding(30, 25, 30, 25)
                radioButtonAfternoon.buttonDrawable = null
                radioButtonAfternoon.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.radiobtn_appointment, null
                )
                radioButtonAfternoon.text = dataList[i].time
                radioButtonAfternoon.gravity = Gravity.CENTER
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_medium)
                radioButtonAfternoon.typeface = typeface
                radioButtonAfternoon.setTextColor(
                    ResourcesCompat.getColor(
                        resources, R.color.grey_dark, null
                    )
                )
                radioButtonAfternoon.id = System.currentTimeMillis().toInt()
                binding.rbAfternoon.addView(radioButtonAfternoon)
            }
        } else {
            binding.clAfternoonTime.visibility = View.GONE
        }
    }

    private fun eveningTimes(dataList: ArrayList<Evening>) {
        if (dataList.isNotEmpty()) {
            binding.clEveningTime.visibility = View.VISIBLE
            binding.rbEvening.removeAllViews()
            for (i in 0 until dataList.size) {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
                if (i - 1 != dataList.size) {
                    params.setMargins(0, 0, 15, 0)
                }
                val radioButtonEvening = RadioButton(requireContext())
                radioButtonEvening.layoutParams = params
                radioButtonEvening.setPadding(30, 25, 30, 25)
                radioButtonEvening.buttonDrawable = null
                radioButtonEvening.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.radiobtn_appointment, null
                )
                radioButtonEvening.text = dataList[i].time
                radioButtonEvening.gravity = Gravity.CENTER
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_medium)
                radioButtonEvening.typeface = typeface
                radioButtonEvening.setTextColor(
                    ResourcesCompat.getColor(
                        resources, R.color.grey_dark, null
                    )
                )
                radioButtonEvening.id = System.currentTimeMillis().toInt()
                binding.rbEvening.addView(radioButtonEvening)
            }
        } else {
            binding.clEveningTime.visibility = View.GONE
        }
    }


}