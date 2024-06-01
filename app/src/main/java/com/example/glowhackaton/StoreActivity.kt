package com.example.glowhackaton

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class StoreActivity : AppCompatActivity() {

    private lateinit var enterEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var commentLayout: LinearLayout
    private lateinit var commentScrollView: ScrollView
    private var commentCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        enterEditText = findViewById(R.id.enter)
        sendButton = findViewById(R.id.send)
        commentLayout = findViewById(R.id.comment)
        commentScrollView = findViewById(R.id.comment_scroll)

        sendButton.setOnClickListener{
            val commentText = enterEditText.text.toString().trim()

            if (commentText.isNotEmpty()) {
                addComment(commentText)
                enterEditText.text.clear()
                scrollToBottom()
            } else {
                Toast.makeText(this, "댓글을 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scrollToBottom() {
        commentScrollView.post {
            commentScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    private fun addComment(commentText: String) {
        val newComment = TextView(this).apply {
            id = View.generateViewId()
            text = commentText
            textSize = 16f
            setTextColor(Color.BLACK)
            setBackgroundResource(R.drawable.speech)
            setPadding(16, 16, 16, 16)
        }
        val layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.BELOW, R.id.comment)
            topMargin = 10
            if (commentCount > 0) {
                val lastCommentId = commentLayout.getChildAt(commentLayout.childCount - 1).id
                addRule(RelativeLayout.BELOW, lastCommentId)
            } else {
                addRule(RelativeLayout.BELOW, R.id.comment)
            }
        }
        commentLayout.addView(newComment, layoutParams)
        commentCount++

    }
}