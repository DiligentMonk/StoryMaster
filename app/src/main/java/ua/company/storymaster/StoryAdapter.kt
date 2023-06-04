package ua.company.storymaster

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.company.storymaster.databinding.StoryItemBinding

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryHolder>() {
    private val titleList = ArrayList<Story>()
    class StoryHolder(item : View) : RecyclerView.ViewHolder(item) {
        private val binding = StoryItemBinding.bind(item)
        fun bind(story: Story) = with(binding) {
            btnStoryItem.text = story.title
            btnStoryItem.setOnClickListener {
                val intent = Intent(binding.root.context, StoryViewer::class.java)
                intent.putExtra("genre", story.genre)
                intent.putExtra("title", story.title)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        return StoryHolder(view)
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(titleList[position])
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    fun addStory(story: Story) {
        titleList.add(story)
        notifyDataSetChanged()
    }
}