package com.ballchalu.ui.contest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.ui.contest.all_contest.ContestFragment
import com.ballchalu.ui.contest.user_contest.UserContestFragment
import com.ccpp.shared.domain.MatchListing
import com.ccpp.shared.util.ConstantsBase
import com.google.android.material.tabs.TabLayout
import java.util.*
import javax.inject.Inject

class MainContestFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    private var matchListing:MatchListing? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_contest, container, false)

        viewPager = root.findViewById(R.id.viewpager) as ViewPager
        setupViewPager(viewPager!!)

        tabLayout = root.findViewById(R.id.tablayout) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)

        val headerView=(activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.custom_contest_tab,null,false)

        val linearLayoutOne = headerView.findViewById(R.id.ll) as LinearLayout
        val linearLayout2 = headerView.findViewById(R.id.ll2) as LinearLayout

        tabLayout!!.getTabAt(0)!!.customView = linearLayoutOne
        tabLayout!!.getTabAt(1)!!.customView = linearLayout2

        val tvMatchName = root.findViewById<TextView>(R.id.tvMatchName)
        tvMatchName.text = matchListing?.title

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            matchListing = it.getSerializable(ConstantsBase.KEY_MATCH_ITEM) as MatchListing?
        }
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ContestFragment().also {
            it.arguments = Bundle().apply {
                putSerializable(ConstantsBase.KEY_MATCH_ITEM, matchListing)
            }
        }, "ONE")
        adapter.addFragment(UserContestFragment().also {
            it.arguments = Bundle().apply {
                putSerializable(ConstantsBase.KEY_MATCH_ITEM, matchListing)
            }
        }, "TWO")
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
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

}
