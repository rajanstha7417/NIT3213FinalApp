package com.example.nit3213.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nit3213.databinding.ActivityLoginBinding
import com.example.nit3213.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/** @AndroidEntryPoint lets Hilt inject the ViewModel into this Activity. */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Campus location options used to build the /{location}/auth URL.
        binding.spinnerLocation.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("footscray", "sydney", "ort")
        )

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                location = binding.spinnerLocation.selectedItem.toString(),
                username = binding.etUsername.text.toString().trim(),
                password = binding.etPassword.text.toString().trim()
            )
        }

        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.visibility =
                        if (state is LoginState.Loading) View.VISIBLE else View.GONE
                    binding.btnLogin.isEnabled = state !is LoginState.Loading
                    binding.tvError.visibility = View.GONE

                    when (state) {
                        is LoginState.Success -> goToDashboard(state.keypass)
                        is LoginState.Error -> {
                            binding.tvError.text = state.message
                            binding.tvError.visibility = View.VISIBLE
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun goToDashboard(keypass: String) {
        startActivity(
            Intent(this, DashboardActivity::class.java)
                .putExtra(DashboardActivity.EXTRA_KEYPASS, keypass)
        )
    }
}
