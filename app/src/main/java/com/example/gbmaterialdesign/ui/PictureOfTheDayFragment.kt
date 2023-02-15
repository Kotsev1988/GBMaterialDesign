package com.example.gbmaterialdesign.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.gbmaterialdesign.BottomNavigationDrawer
import com.example.gbmaterialdesign.MainActivity
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.databinding.FragmentPictureBinding
import com.example.gbmaterialdesign.ui.AppSatates.AppState
import com.example.gbmaterialdesign.ui.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val dateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd")

        binding.textInputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.textInputEdit.text.toString()}")
            })
        }

        viewModel.getPictureLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        val currentDate = dateFormat.format(Date())
        viewModel.sendRequest(currentDate)

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.day_before_yesterday -> {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DATE, -2)
                    val dayBeforeYesterday = dateFormat.format(calendar.time)
                    viewModel.sendRequest(dayBeforeYesterday)
                }

                R.id.yesterday -> {

                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DATE, -1)
                    val yesterday = dateFormat.format(calendar.time)
                    viewModel.sendRequest(yesterday)
                }

                R.id.today -> {

                    val currentDate = dateFormat.format(Date())
                    viewModel.sendRequest(currentDate)
                }
            }
        }

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {

            R.id.action_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container1, SettingsFragment())
                ?.addToBackStack(null)
                ?.commit()

            R.id.action_favorite -> {

            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawer().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(it: AppState) {

        when (it) {
            is AppState.Success -> {

                binding.frameLoading.visibility = View.GONE
                binding.pictureOfDay.load(
                    it.pictureOfTheDay.url
                ) {
                    lifecycle(this@PictureOfTheDayFragment)
                    crossfade(true)
                }
                binding.pictureOfDay.setOnClickListener { view ->
                    val bundle = Bundle()
                    bundle.putString(MyBottomSheetDialog.BUNDLE_EXTRA,
                        it.pictureOfTheDay.explanation)

                    val myBottomDialog = MyBottomSheetDialog.newInstance(bundle)

                    myBottomDialog.show(childFragmentManager, MyBottomSheetDialog.TAG)
                }

            }
            is AppState.Error -> {

                binding.frameLoading.visibility = View.GONE
                val error = it.error

                error.message?.let { it1 ->
                    binding.main.showSnackBar(
                        it1,
                        getString(R.string.reload),

                        {
                            val dateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd")
                            viewModel.sendRequest(dateFormat.format(Date()))
                        }
                    )
                }

            }
            is AppState.Loading -> {
                binding.frameLoading.visibility = View.VISIBLE
            }
        }
    }

    fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE,
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

}