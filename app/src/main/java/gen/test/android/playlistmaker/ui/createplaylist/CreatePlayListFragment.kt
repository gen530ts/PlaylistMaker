package gen.test.android.playlistmaker.ui.createplaylist

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
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
import java.io.File
import java.io.FileOutputStream


class CreatePlayListFragment : Fragment() {

    private val viewModel: CreatePlayListViewModel by viewModel()
    private lateinit var binding: FragmentCreatePlayListBinding
    private var uriCover: Uri? = null
    private var imageSaveFileName: String? = null
    private var fullImageSaveFileName: String? = null
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
              //  binding.coverIvPl.setImageURI(uri)
                Glide.with(requireActivity())
                   // .asBitmap()

                    .load(uri)

               //     .centerInside()
                    .placeholder(R.drawable.add_photo)
                    .transform(RoundedCorners(8))

                   //
                   // .override(312, 312)
                    .into(binding.coverIvPl)
                uriCover = uri
            }
        }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if ((uriCover != null) || (binding.enterPlName.text.isNotEmpty()) || binding.enterPlDescr
                    .text.isNotEmpty()
            ) {
                MaterialAlertDialogBuilder(context!!).setTitle("Завершить создание плейлиста?")
                    .setMessage("Все несохраненные данные будут потеряны")
                    .setNeutralButton("Отмена") { _, _ ->
                        Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_LONG).show()
                    }.setPositiveButton("Завершить") { _, _ ->
                        Toast.makeText(requireContext(), "Завершить", Toast.LENGTH_LONG).show()
                        //callback.Enabled
                        this.isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }.show()
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
        activity?.onBackPressedDispatcher?.addCallback(this, callback)

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

    private fun savePhoto() {
        val filePath = File(
            requireActivity().getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ), "playlistmaker_album"
        )
        Log.d("mytag", "filePath = $filePath")
//создаем каталог, если он не создан
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
//создаём экземпляр класса File, который указывает на файл внутри каталога

        val file = File(filePath, imageSaveFileName!!)
        fullImageSaveFileName = file.toString()
// создаём входящий поток байтов из выбранной картинки
        val inputStream = requireActivity().contentResolver.openInputStream(uriCover!!)
// создаём исходящий поток байтов в созданный выше файл
        val outputStream = FileOutputStream(file)
// записываем картинку с помощью BitmapFactory  requireActivity().contentResolver.
        val isSaved = BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        Toast.makeText(requireContext(), "File save: $isSaved", Toast.LENGTH_LONG).show()

    }

    private fun saveInDb() {
        viewModel.addPlaylist(
            name = binding.enterPlName.text.toString(),
            descr = binding.enterPlDescr.text.toString(),
            imagePath = uriCover?.toString() ?:""
        )
    }
}