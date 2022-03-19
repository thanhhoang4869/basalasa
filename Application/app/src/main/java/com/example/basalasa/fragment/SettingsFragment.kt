package com.example.basalasa.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basalasa.R
import com.example.basalasa.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.layoutPersonal.setOnClickListener { clickPersonal() }
//        binding.layoutPassword.setOnClickListener { clickChangePassword() }
//        binding.layoutTopUp.setOnClickListener { clickTopUp() }
//        binding.layoutReport.setOnClickListener { clickReport() }
//        binding.logoutButton.setOnClickListener { clickLogout() }
    }
//
//    private fun clickPersonal() {
//        activity?.let {
//            val intent = Intent(context, ChangeInformationActivity::class.java)
//            it.startActivity(intent)
//        }
//    }

//    private fun clickChangePassword() {
//        activity?.let {
//            val intent = Intent(it, ChangePasswordActivity::class.java)
//            it.startActivity(intent)
//        }
//    }
//
//    private fun clickTopUp() {
//        activity?.let {
//            val intent = Intent(it, TopUpActivity::class.java)
//            it.startActivity(intent)
//        }
//    }
//
//    private fun clickReport() {
//        activity?.let {
//            val intent = Intent(it, ReportActivity::class.java)
//            it.startActivity(intent)
//        }
//    }
//
//    private fun clickLogout() {
//        activity?.let {
//            Cache.clear(it)
//            val intent = Intent(it, LoginActivity::class.java)
//            it.startActivity(intent)
//            it.finish()
//        }
//    }
}