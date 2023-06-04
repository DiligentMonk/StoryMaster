package ua.company.storymaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import ua.company.storymaster.databinding.ActivityStoryListBinding

class StoryList : AppCompatActivity() {
    private lateinit var binding : ActivityStoryListBinding
    private lateinit var database : DatabaseReference
    private val adapter = StoryAdapter()
    val titles = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        val genre = intent.getIntExtra("selectedGenre", 0)
        var genreT = ""

        when (genre) {
            0 -> genreT = "horror_stories"
            1 -> genreT = "detective_stories"
        }

        val story = database.child(genreT)
        story.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val title = childSnapshot.child("title").getValue(String::class.java)
                    if (title != null) {
                        titles.add(title)
                    }
                }
                init(genre)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failure")
            }
        })
    }

    private fun init(genre: Int) = with(binding){
        rvList.layoutManager = LinearLayoutManager(this@StoryList)
        rvList.adapter = adapter
        for (title in titles) {
            val story = Story(genre, title)
            adapter.addStory(story)
        }
    }
}