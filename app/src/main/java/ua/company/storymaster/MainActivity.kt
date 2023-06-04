package ua.company.storymaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ua.company.storymaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    private var i = 0
    private val listedGenres = listOf(R.string.horror_text, R.string.detective_text)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        binding.btnNext.setOnClickListener {
            if ((i+1) in listedGenres.indices) {
                i++
                binding.tvMenu.text = getString(listedGenres[i])
            }
        }
        binding.btnPrev.setOnClickListener {
            if ((i-1) in listedGenres.indices) {
                i--
                binding.tvMenu.text = getString(listedGenres[i])
            }
        }
    }

    fun onClickOpenStoryList(view: View) {
        val intent = Intent(this, StoryList::class.java)
        intent.putExtra("selectedGenre", i)
        startActivity(intent)
    }
}