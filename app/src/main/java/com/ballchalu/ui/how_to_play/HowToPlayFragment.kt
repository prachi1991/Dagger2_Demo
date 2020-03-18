package com.ballchalu.ui.how_to_play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentHowToPlayBinding
import com.ballchalu.ui.how_to_play.adapter.HowToPlayAdapter
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HowToPlayFragment : BaseFragment() {

    private var howToPlayAdapter: HowToPlayAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentHowToPlayBinding
    private lateinit var viewModel: HowToPlayViewModel

    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentHowToPlayBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HowToPlayFragment

            howToPlayAdapter = HowToPlayAdapter()
            layoutManager = LinearLayoutManager(context)
            rvHowToPlay.layoutManager = layoutManager
            rvHowToPlay.adapter = howToPlayAdapter


        }
        return binding.root
    }

}
