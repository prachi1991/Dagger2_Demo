package com.ballchalu.ui.contest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMainContestBinding
import com.ballchalu.ui.contest.all_contest.ContestFragment
import com.ballchalu.ui.contest.user_contest.UserContestFragment
import com.ccpp.shared.domain.MatchListing
import com.ccpp.shared.util.ConstantsBase
import java.util.*
import javax.inject.Inject

class MainContestFragment : BaseFragment(), ContestCountListener {
    private var binding: FragmentMainContestBinding? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var matchListing: MatchListing? = null
    private var isDeclared: Boolean = false
    private var isMatchStarted: Boolean = false
    private var allContest = 0
    private var myContest = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainContestBinding.inflate(inflater, container, false)

        setupViewPager()
        initTabLayout()

        onAllContest(allContest)
        onUserContest(myContest)

        binding?.tvMatchName?.text = matchListing?.title

        return binding?.root
    }

    private fun initTabLayout() {
        val headerView =
            (activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.custom_contest_tab, null, false)

        val linearLayoutOne = headerView.findViewById(R.id.ll) as LinearLayout
        val linearLayout2 = headerView.findViewById(R.id.ll2) as LinearLayout

        binding?.tablayout?.getTabAt(0)?.customView = linearLayoutOne
        binding?.tablayout?.getTabAt(1)?.customView = linearLayout2

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            matchListing = it.getSerializable(ConstantsBase.KEY_MATCH_ITEM) as MatchListing?
            if (it.containsKey(ConstantsBase.KEY_DECLARED)) {
                isDeclared = it.getBoolean(ConstantsBase.KEY_DECLARED)
            }
            if (it.containsKey(ConstantsBase.KEY_IS_MATCH_STARTED))
                isMatchStarted = it.getBoolean(ConstantsBase.KEY_IS_MATCH_STARTED)
        }
    }


    private fun setupViewPager() {
        binding?.tablayout?.setupWithViewPager(binding?.viewpager)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ContestFragment().also {
            it.setListeners(this)
            it.arguments = Bundle().apply {
                putSerializable(ConstantsBase.KEY_MATCH_ITEM, matchListing)
                putBoolean(ConstantsBase.KEY_DECLARED, isDeclared)
                putBoolean(ConstantsBase.KEY_IS_MATCH_STARTED, isMatchStarted)
            }
        }, "ONE")
        adapter.addFragment(UserContestFragment().also {
            //            it.setListeners(this)
            it.arguments = Bundle().apply {
                putSerializable(ConstantsBase.KEY_MATCH_ITEM, matchListing)
                putBoolean(ConstantsBase.KEY_DECLARED, isDeclared)
                putBoolean(ConstantsBase.KEY_IS_MATCH_STARTED, isMatchStarted)
            }
        }, "TWO")
        binding?.viewpager?.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()


        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun onAllContest(count: Int) {
        allContest = count
        binding?.tablayout?.get(0)?.findViewById<TextView>(R.id.tvCountAll)?.text = count.toString()
    }

    override fun onUserContest(count: Int) {
        myContest = count
        binding?.tablayout?.get(0)?.findViewById<TextView>(R.id.tvCountMy)?.text = count.toString()
    }
}
