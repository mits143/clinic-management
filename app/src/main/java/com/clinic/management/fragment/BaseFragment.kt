package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseFragment<B : ViewBinding> : DialogFragment(), CoroutineScope by CoroutineScope(
    Dispatchers.Main
) {

    protected lateinit var binding: B
        private set

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(msg: Int) {
        Toast.makeText(context, getString(msg), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        coroutineContext[Job]?.cancel()
        super.onDestroyView()
    }
}