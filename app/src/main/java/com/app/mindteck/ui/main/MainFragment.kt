package com.app.mindteck.ui.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope

import com.app.mindteck.base.GenericSimpleRecyclerBindingInterface
import com.app.mindteck.base.SimpleGenericRecyclerAdapter

import com.app.mindteck.model.Country
import com.app.mindteck.ui.main.adapter.ViewPagerAdapter
import com.app.mindteck.utils.log
import com.app.rfid.R
import com.app.rfid.databinding.FragmentMainBinding
import com.app.rfid.databinding.ItemCountryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val list = mutableListOf<Country>()
    private val sliderList = mutableListOf(
        "https://images.pexels.com/photos/4676396/pexels-photo-4676396.jpeg?auto=compress&cs=tinysrgb&w=600",
        "https://images.pexels.com/photos/8617808/pexels-photo-8617808.jpeg?auto=compress&cs=tinysrgb&w=600",
        "https://images.pexels.com/photos/5264006/pexels-photo-5264006.jpeg?auto=compress&cs=tinysrgb&w=600",
        "https://images.pexels.com/photos/5264006/pexels-photo-5264006.jpeg?auto=compress&cs=tinysrgb&w=600"
    )
    private lateinit var mViewModel: MainViewModel
    private lateinit var mainBinding: FragmentMainBinding
    private val mAdapter by lazy {
        SimpleGenericRecyclerAdapter<Country>(
            list,
            R.layout.item_country,
            bindingInterface
        )
    }
    private val mSliderAdapter by lazy {
        ViewPagerAdapter(
            requireContext(),
            sliderList
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = DataBindingUtil.inflate<FragmentMainBinding?>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
            adapter = mAdapter
            slider = mSliderAdapter
            mainFragment = this@MainFragment
        }

        indicatorViewPager()
        searchEditor()
        observeData()
        return mainBinding.root
    }

    private fun indicatorViewPager() {
        mainBinding.indTab.setupWithViewPager(mainBinding.viewPager)
    }

    private fun searchEditor() {
        mainBinding.evSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mainBinding.evSearch.clearFocus()
                    return false
                }
                return false
            }
        })
        mainBinding.evSearch.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            mainBinding.isFocused = hasFocus

        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            mViewModel.countryList.flowWithLifecycle(lifecycle).collectLatest {
                it?.let {
                    list.clear()
                    list.addAll(it)
                    mAdapter.notifyDataSetChanged()
                }
            }


        }


    }

    private val bindingInterface = object : GenericSimpleRecyclerBindingInterface<Country> {
        override fun bindData(item: Country, view: View) {
            DataBindingUtil.bind<ItemCountryBinding>(view)?.apply {
                country = item
            }
        }
    }

    fun onTextChanged(text: CharSequence) {
        "$text".log()
        list.clear()
        if (text.isEmpty()) {
            mViewModel.countryList.value?.let { list.addAll(it) }
        } else {
            mViewModel.countryList.value?.let {
                list.addAll(it.filter { country ->
                    country.name.startsWith(
                        text,true

                    )
                })
            }
        }
        mAdapter.notifyDataSetChanged()

    }

}

