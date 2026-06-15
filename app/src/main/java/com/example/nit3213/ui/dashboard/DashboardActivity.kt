package com.example.nit3213.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nit3213.data.model.Entity
import com.example.nit3213.databinding.ActivityDashboardBinding
import com.example.nit3213.ui.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEYPASS = "extra_keypass"
    }

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        observeState()

        val keypass = intent.getStringExtra(EXTRA_KEYPASS).orEmpty()
        viewModel.loadDashboard(keypass)
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.visibility =
                        if (state is DashboardState.Loading) View.VISIBLE else View.GONE
                    binding.tvError.visibility = View.GONE

                    when (state) {
                        is DashboardState.Success -> showEntities(state.entities)
                        is DashboardState.Error -> {
                            binding.tvError.text = state.message
                            binding.tvError.visibility = View.VISIBLE
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun showEntities(entities: List<Entity>) {
        binding.tvCount.text = "Total: ${entities.size}"
        binding.recyclerView.adapter = EntityAdapter(entities) { entity ->
            startActivity(
                Intent(this, DetailsActivity::class.java)
                    .putExtra(DetailsActivity.EXTRA_ENTITY, entity)
            )
        }
    }
}
