package gen.test.android.playlistmaker.ui.settings.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gen.test.android.playlistmaker.databinding.FragmentSettingsBinding
import gen.test.android.playlistmaker.ui.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {
    private val settingsViewModel by viewModel<SettingsViewModel>()
    private lateinit var binding: FragmentSettingsBinding


    private fun setShareListener() {
        val share = binding.shareImageView//findViewById<TextView>(R.id.shareTextView)
        share.setOnClickListener {settingsViewModel.sharingApp() }
    }
    private fun setSupportListener() {
        val support = binding.supportImageView//findViewById<TextView>(R.id.supportTextView)
        support.setOnClickListener {settingsViewModel.openSupport() }
    }
    private fun setAgreeListener() {
        val agree = binding.agreeImageView//findViewById<TextView>(R.id.agreeTextView)
        agree.setOnClickListener {settingsViewModel.openTerms() }
    }





    private fun setDarkListener() {
        val themeSwitcher = binding.themeSwitcher//findViewById<SwitchMaterial>(R.id.themeSwitcher)
        settingsViewModel.getThemeLiveData().observe(viewLifecycleOwner) {

            themeSwitcher.isChecked =  it.isDark
        }
        settingsViewModel.getTheme()

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            settingsViewModel.switchTheme(checked)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setShareListener()
        setSupportListener()
        setAgreeListener()
        setDarkListener()
    }
}