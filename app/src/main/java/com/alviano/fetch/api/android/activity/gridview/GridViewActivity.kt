package com.alviano.fetch.api.android.activity.gridview

import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alviano.fetch.api.android.R
import com.alviano.fetch.api.android.databinding.ActivityGridViewBinding

class GridViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGridViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGridViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView: Array<Int> = arrayOf(
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
        )

        val titleName: Array<String> = arrayOf(
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai",
            "Hai"
        )

        val gridAdapter: GridAdapter = GridAdapter()
        gridAdapter.girdAdapter(this, imageView, titleName)

        binding.allItems.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "You Clicked on ${titleName[position]}", Toast.LENGTH_SHORT).show()
        }

    }
}