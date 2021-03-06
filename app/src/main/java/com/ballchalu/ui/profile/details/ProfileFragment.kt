package com.ballchalu.ui.profile.details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentProfileBinding
import com.ballchalu.utils.BlurBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.rxjava.RxBus
import com.ballchalu.shared.rxjava.RxEvent
import com.ballchalu.shared.util.viewModelProvider
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs
import javax.inject.Inject
import kotlin.math.roundToInt


class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPreferenceStorage


    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentProfileBinding.inflate(inflater).apply {
            lifecycleOwner = this@ProfileFragment
            tvEmailValue.text = sharedPref.userEmail
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvEditProfile.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
        viewModel.userDetails.observe(viewLifecycleOwner, EventObserver {
            it?.let {
                binding.llBody.isVisible = true
                binding.collapsingToolbar.title = "${it.firstName} ${it.lastName}"
                binding.tvEmailValue.text = it.email.toString()
                binding.tvBcCoinValue.text = it.bc_coins.toString()
                binding.tvAvailCoinValue.text = it.available_coins.toString()
                binding.tvUserNameValue.text = it.user_name.toString()
                binding.toolbar.title = it.user_name.toString()
                binding.tvCoinValue.text = it.coins.toString()
            }
        })
        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.callUserDetails()
        viewModel.loadProfile.observe(viewLifecycleOwner, EventObserver { image ->
            val resultBmp: Bitmap = BlurBuilder.blur(requireContext(), image)
            Glide.with(requireContext())
                .load(resultBmp)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.ivProfileBlur)

            Glide.with(requireContext())
                .load(image)
                .into(binding.ivProfile)
            RxBus.publish(RxEvent.UpdateProfile(bitmap = image))
        })

        initAnimation()
    }


    private fun initAnimation() {
        PADDING_RIGHT = resources.getDimensionPixelOffset(R.dimen._20sdp).toFloat()
        EXPAND_AVATAR_SIZE = resources.getDimensionPixelOffset(R.dimen._80sdp).toFloat()
        COLLAPSE_IMAGE_SIZE = resources.getDimensionPixelOffset(R.dimen._30sdp).toFloat()
        val size = resources.getDimensionPixelOffset(R.dimen._200sdp).toFloat()
        binding.appbar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
                if (isCalculated.not()) {
                    avatarAnimateStartPointY =
                        abs((appBarLayout.height - (size + horizontalToolbarAvatarMargin)) / appBarLayout.totalScrollRange)
                    avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
                    verticalToolbarAvatarMargin = (binding.toolbar.height - COLLAPSE_IMAGE_SIZE) * 2
                    isCalculated = true
                }
                updateViews(abs(i / appBarLayout.totalScrollRange.toFloat()))
            })
    }

    companion object {
        private var EXPAND_AVATAR_SIZE: Float = 0F
        private var COLLAPSE_IMAGE_SIZE: Float = 0F
        private var horizontalToolbarAvatarMargin: Float = 0F
        private var currentOffset: Float = 0F

        /**/
        private var avatarAnimateStartPointY: Float = 0F
        private var avatarCollapseAnimationChangeWeight: Float = 0F
        private var isCalculated = false
        private var verticalToolbarAvatarMargin = 0F
        private var PADDING_RIGHT = 15f

    }

    private fun updateViews(offset: Float) {
        if (currentOffset != offset) {
            currentOffset = offset
            binding.ivProfile.apply {
                when {
                    offset > avatarAnimateStartPointY -> {
                        val avatarCollapseAnimateOffset =
                            (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
                        val avatarSize =
                            EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * avatarCollapseAnimateOffset
                        val lp = layoutParams
                        lp.width = avatarSize.roundToInt()
                        lp.height = avatarSize.roundToInt()
                        layoutParams = lp
                        this.translationX =
                            ((binding.appbar.width - horizontalToolbarAvatarMargin - COLLAPSE_IMAGE_SIZE - PADDING_RIGHT)) * avatarCollapseAnimateOffset
                    }
                    else -> this.layoutParams.also {
                        if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                            it.height = EXPAND_AVATAR_SIZE.toInt()
                            it.width = EXPAND_AVATAR_SIZE.toInt()
                            this.layoutParams = it
                        }
                        translationX = 0f
                    }
                }
            }
        }
    }

}
