package gen.test.android.playlistmaker.ui.createplaylist

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.databinding.FragmentCreatePlayListBinding
import gen.test.android.playlistmaker.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreatePlayListFragment : Fragment() {

    private val viewModel: CreatePlayListViewModel by viewModel()
    private lateinit var binding: FragmentCreatePlayListBinding
    private var uriCover: Uri? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri
            ->
            if (uri != null) {
                Glide.with(requireActivity())
                    .load(uri)
                    .fitCenter()
                    .placeholder(R.drawable.add_photo)
                    .transform(RoundedCorners(8))
                    .into(binding.coverIvPl)

                uriCover = uri
            }
        }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if ((uriCover != null) || (binding.enterPlName.text.isNotEmpty()) || binding.enterPlDescr
                    .text.isNotEmpty()
            ) {
                val dial=MaterialAlertDialogBuilder(requireContext()).setTitle("Завершить создание" +
                        " плейлиста?")
                    .setMessage("Все несохраненные данные будут потеряны")
                    .setNeutralButton("Отмена") { _, _ ->
                    }.setPositiveButton("Завершить") { _, _ ->
                        this.isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }.show()
                dial.window?.setBackgroundDrawableResource(R.color.yp_blue)
            } else {
                this.isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePlayListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterPlName.addTextChangedListener(
            onTextChanged = { charSequence, _, _, _ ->
                binding.createPlBtn.isEnabled = charSequence?.isEmpty() != true
            }
        )
        binding.coverIvPl.setOnClickListener { loadPhoto() }
        binding.createPlBtn.setOnClickListener {
            saveInDb()
            findNavController().popBackStack()
        }
        binding.backImageView.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

        viewModel.observeData().observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Success -> showSuccess(it.data)
                else -> {}
            }
        }
    }


    private fun showSuccess(name:String) {
        Toast.makeText(requireContext(), "Плейлист $name создан", Toast.LENGTH_LONG).show()
        viewModel.resetLd()
    }

    private fun loadPhoto() {
        val permissionProvided = ContextCompat.checkSelfPermission(
            requireContext(), Manifest
                .permission.READ_EXTERNAL_STORAGE
        )
        if (permissionProvided == PackageManager.PERMISSION_GRANTED) {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else if (permissionProvided == PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }


    private fun saveInDb() {
        viewModel.addPlaylist(
            name = binding.enterPlName.text.toString(),
            descr = binding.enterPlDescr.text.toString(),
            imagePath = uriCover?.toString() ?:""
        )
    }
}