package com.ballchalu.ui.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentSignInBinding
import com.ballchalu.utils.AppConstants
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.util.viewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import timber.log.Timber
import javax.inject.Inject


class SignInFragment : BaseFragment(), GoogleApiClient.OnConnectionFailedListener {
    private var mGoogleSignInClient: GoogleSignInClient? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentSignInBinding.inflate(inflater).apply {
            lifecycleOwner = this@SignInFragment
        }

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initGoogleAndFB()

        viewModel.loginFormState.observe(viewLifecycleOwner, EventObserver {
            val loginState = it ?: return@EventObserver

            // disable login button unless both tvUsernameValue / tvPasswordValue is valid

            if (loginState.usernameError != null) {
                binding.tvUsernameValue.error = getString(loginState.usernameError!!)
            }
            if (loginState.passwordError != null) {
                binding.tvPasswordValue.error = getString(loginState.passwordError!!)
            }
            if (it.isDataValid) {
                viewModel.callLogin(
                    binding.tvUsernameValue.toString(),
                    binding.tvPasswordValue.toString()
                )
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            val loginResult = it ?: return@EventObserver

            updateUiWithUser(loginResult.status)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.login.isEnabled = !it
        })

        binding.login.setOnClickListener {
            //            viewModel.callLogin("", "")
            viewModel.validateData(
                binding.tvUsernameValue.text.toString(),
                binding.tvPasswordValue.text.toString()
            )
        }

        binding.llGoogleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, AppConstants.GOOGLE_SIGN_IN_REQUEST)
    }

    private fun initGoogleAndFB() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    private fun updateUiWithUser(status: String?) {
        Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.GOOGLE_SIGN_IN_REQUEST) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            updateUiWithUser(account?.email)
        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.d("signInResult:failed code=%s", e.statusCode)
            updateUiWithUser("Something went wrong " + e.message)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}
