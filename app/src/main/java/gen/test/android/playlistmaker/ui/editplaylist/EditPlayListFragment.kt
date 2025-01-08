package gen.test.android.playlistmaker.ui.editplaylist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.ui.createplaylist.CreatePlayListFragment
import gen.test.android.playlistmaker.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditPlayListFragment:CreatePlayListFragment() {
    override val viewModel: EditPlayListViewModel by viewModel{
        parametersOf(requireArguments().getLong(PLIST_EDIT_FRAGMENT))
    }
    companion object {
        private const val PLIST_EDIT_FRAGMENT = "PLIST_EDIT_FRAGMENT"
        fun createArgs(idPlist: Long): Bundle =
            bundleOf(PLIST_EDIT_FRAGMENT to idPlist)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initPlistInfo()
        headerTV.text= getString(R.string.edit_plist)
        binding.createPlBtn.text= getString(R.string.save_plist)
    }

    override fun observe() {
        viewModel.observeData().observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Success -> initInfo(it.data)
                else -> {}
            }
        }
    }

    override fun btnOnClick() {

        saveInDb()
    }

    override fun addOnBackPressedCallback() {

    }

            private fun initInfo(plist: Plist) {
                if(plist.name=="") {
                    findNavController().popBackStack()
                }else{
                    if (plist.imageUri != null) {
                        binding.coverIvPl.setImageURI(plist.imageUri)
                    } else binding.coverIvPl.setImageResource(R.drawable.placeholder_track)
                    plistId=plist.id
                    binding.enterPlName.setText(plist.name)
                    binding.enterPlDescr.setText(plist.description)
                }

    }

}


