package com.janaushadhi.finder.ui.ai

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.ai.GeminiService
import kotlinx.coroutines.launch

class AiChatActivity : AppCompatActivity() {

    private lateinit var geminiService: GeminiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var inputField: EditText
    private lateinit var sendButton: Button
    private lateinit var progressBar: ProgressBar
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "AI Assistant"

        geminiService = GeminiService()

        recyclerView = findViewById(R.id.recycler_view)
        inputField = findViewById(R.id.et_input)
        sendButton = findViewById(R.id.btn_send)
        progressBar = findViewById(R.id.progress_bar)

        setupRecyclerView()

        sendButton.setOnClickListener {
            val question = inputField.text.toString().trim()
            if (question.isNotBlank()) {
                sendMessage(question)
            }
        }

        // Add welcome message
        messages.add(ChatMessage("Hello! I'm your Jan-Aushadhi AI assistant. Ask me about medicines, generic alternatives, or Jan-Aushadhi stores.", false))
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun sendMessage(question: String) {
        // Add user message
        messages.add(ChatMessage(question, true))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)

        inputField.text.clear()
        progressBar.visibility = View.VISIBLE
        sendButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = geminiService.askMedicineQuestion(question)
                messages.add(ChatMessage(response, false))
                adapter.notifyItemInserted(messages.size - 1)
                recyclerView.scrollToPosition(messages.size - 1)
            } catch (e: Exception) {
                messages.add(ChatMessage("Sorry, I couldn't process your request. Please try again.", false))
                adapter.notifyItemInserted(messages.size - 1)
            } finally {
                progressBar.visibility = View.GONE
                sendButton.isEnabled = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    data class ChatMessage(val text: String, val isUser: Boolean)

    private class ChatAdapter(private val messages: List<ChatMessage>) :
        RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ChatViewHolder {
            val layoutId = if (viewType == 1) R.layout.item_chat_user else R.layout.item_chat_ai
            val view = android.view.LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return ChatViewHolder(view)
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            holder.bind(messages[position])
        }

        override fun getItemCount() = messages.size

        override fun getItemViewType(position: Int) = if (messages[position].isUser) 1 else 0

        class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: android.widget.TextView = itemView.findViewById(R.id.tv_message)

            fun bind(message: ChatMessage) {
                textView.text = message.text
            }
        }
    }
}
