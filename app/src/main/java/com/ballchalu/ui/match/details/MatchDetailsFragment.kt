package com.ballchalu.ui.match.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchDetailsBinding
import com.ballchalu.ui.match.details.adapter.EndingDigitAdapter
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.match_details.Market
import com.ccpp.shared.domain.match_details.Match
import com.ccpp.shared.domain.match_details.Runner
import com.ccpp.shared.domain.match_details.SessionsItem
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class MatchDetailsFragment : BaseFragment() {

    private var sessionAdapter: SessionAdapter? = null
    private var endingDigitAdapter: EndingDigitAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMatchDetailsBinding
    private lateinit var viewModel: MatchDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentMatchDetailsBinding.inflate(inflater).apply {
            lifecycleOwner = this@MatchDetailsFragment
        }

        arguments?.let {
            viewModel.matchId = it.getInt(ConstantsBase.KEY_PROVIDER_ID)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSessionAdapterAdapter()
        initEndingDigitAdapterAdapter()

        viewModel.matchResult.observe(viewLifecycleOwner, EventObserver {
            setMatchData(it.match)
        })

        viewModel.sessionEvent.observe(viewLifecycleOwner, EventObserver {
            setSessionData(it)
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.winnerMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setMarketData(it)
        })

        viewModel.evenOddMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setEvenOddData(it)
        })

        viewModel.endingDigitMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setEndingData(it)
        })

        viewModel.callMatchDetailsAsync()
    }

    private fun setEvenOddData(market: Market) {
        binding.layoutEvenOdd.frameEvenOdd.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE

        binding.layoutEvenOdd.tvEvenOddType.text = market.betfairMarketType
        binding.layoutEvenOdd.tvTeam1Back.text =
            if (market.runners?.get(0)?.runner?.canBack == true) market.runners?.get(0)?.runner?.back else ""
        binding.layoutEvenOdd.tvTeam2Back.text =
            if (market.runners?.get(1)?.runner?.canBack == true) market.runners?.get(1)?.runner?.back else ""
    }


    private fun setMatchData(match: Match?) {
        binding.tvMatchTeam1.text = match?.team1
        binding.tvMatchTeam2.text = match?.team2

        binding.tvBatTeamName.text = match?.score?.batteamname
        binding.tvBallTeamName.text = match?.score?.bwlteamname

        binding.tvBatTeamScore.text = match?.score?.batteamdesc
        binding.tvBallTeamScore.text =
            if (match?.score?.bwlteamdesc.isNullOrEmpty()) "Bowling..." else match?.score?.bwlteamdesc
        binding.tvBetStatus.text = match?.heroicCommentary?.event ?: ""

    }

    private fun setMarketData(market: Market?) {
        binding.tvMarketType.text = market?.heroicMarketType
        val items1: Runner? = market?.runners?.get(0)?.runner
        val items2: Runner? = market?.runners?.get(1)?.runner

        setBatTeamBhaav(items1?.back, items1?.lay, items1?.canBack, items1?.canLay)
        setBwlTeamBhaav(items2?.back, items2?.lay, items2?.canBack, items2?.canLay)
    }

    private fun setSessionData(sessionList: List<SessionsItem>?) {
        binding.llSessionSection.visibility =
            if (sessionList?.isNotEmpty() == true) View.VISIBLE else View.GONE
        binding.rvSession.visibility =
            if (sessionList?.isNotEmpty() == true) View.VISIBLE else View.GONE
        sessionAdapter?.setItemList(sessionList)
    }

    private fun setEndingData(market: Market) {
        binding.llEndingDigitSection.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
        binding.tvEvenOddType.text = market.betfairMarketType
        endingDigitAdapter?.setItemList(market.runners)
    }


    private fun initSessionAdapterAdapter() {
        sessionAdapter = SessionAdapter()
        binding.rvSession.adapter = sessionAdapter
    }

    private fun initEndingDigitAdapterAdapter() {
        endingDigitAdapter = EndingDigitAdapter()
        binding.rvEndingDigit.adapter = endingDigitAdapter
    }

//    private fun initEvenOddAdapter() {
//        evenOddAdapter = EvenOddAdapter()
//        binding.rvEvenOdd.adapter = evenOddAdapter
//    }

    private fun setBatTeamBhaav(back: String?, lay: String?, isBack: Boolean?, isLay: Boolean?) {
        binding.tvTeam1Lay.text = if (isBack == true) back else ""
        binding.tvTeam1Back.text = if (isLay == true) lay else ""
    }

    private fun setBwlTeamBhaav(back: String?, lay: String?, isBack: Boolean?, isLay: Boolean?) {
        binding.tvTeam2Lay.text = if (isBack == true) back else ""
        binding.tvTeam2Back.text = if (isLay == true) lay else ""
    }
}