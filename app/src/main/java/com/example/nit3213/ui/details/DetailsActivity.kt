package com.example.nit3213.ui.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.nit3213.data.model.Entity
import com.example.nit3213.databinding.ActivityDetailsBinding
import com.example.nit3213.databinding.ItemDetailRowBinding

/**
 * Details screen. Shows EVERY field of the selected entity, including the
 * description. The Entity is passed in from the Dashboard via the Intent.
 */
class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ENTITY = "extra_entity"
    }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entity = getEntityExtra() ?: run { finish(); return }

        binding.tvTitle.text = entity.title

        // Add a labelled row for each property (description included).
        entity.properties.forEach { (key, value) ->
            val row = ItemDetailRowBinding.inflate(
                LayoutInflater.from(this), binding.detailContainer, false
            )
            row.tvLabel.text = key
            row.tvValue.text = value
            binding.detailContainer.addView(row.root)
        }
    }

    @Suppress("DEPRECATION")
    private fun getEntityExtra(): Entity? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_ENTITY, Entity::class.java)
        } else {
            intent.getSerializableExtra(EXTRA_ENTITY) as? Entity
        }
}
