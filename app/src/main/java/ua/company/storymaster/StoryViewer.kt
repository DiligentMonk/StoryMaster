package ua.company.storymaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import ua.company.storymaster.databinding.ActivityStoryViewerBinding

class StoryViewer : AppCompatActivity() {
    private lateinit var binding : ActivityStoryViewerBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        val genre = intent.getIntExtra("genre", 0)
        val title = intent.getStringExtra("title")
        var genreT = ""

        when (genre) {
            0 -> genreT = "horror_stories"
            1 -> genreT = "detective_stories"
        }

        val storyText = database.child(genreT)
        binding.tvTitle.text = title
        storyText.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val currentText = childSnapshot.child("text").getValue(String::class.java)
                    val currentTitle = childSnapshot.child("title").getValue(String::class.java)
                    if (currentTitle == title) {
                        binding.tvText.text = currentText
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.tvText.text = "Story not found"
            }
        })
    }
}