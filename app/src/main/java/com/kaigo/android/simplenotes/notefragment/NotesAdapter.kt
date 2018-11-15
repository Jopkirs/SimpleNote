package com.kaigo.android.simplenotes.notefragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.kaigo.android.simplenotes.R

class NotesAdapter(val items: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val isNote = 0
    private val isEditNote = 1
    private lateinit var mEditTextTitle: EditText
    private lateinit var mEditTextNote: EditText

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            isNote -> NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_note, parent, false))
            isEditNote -> EditNoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_edit_note, parent, false))
            else -> NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_note, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            isNote -> {
                holder as NoteHolder
                holder.editTextTitle.text = (items[position] as Note).noteTitle
                holder.editTextNote.text = (items[position] as Note).noteNote
            }
            isEditNote -> {
                holder as EditNoteHolder
                mEditTextTitle = holder.editTextTitle
                mEditTextNote = holder.editTextNote
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Note -> isNote
            is EditNoteObject -> isEditNote
            else -> -1
        }
    }

    fun getTitleAndNote(): ArrayList<String> {
        return arrayListOf(mEditTextTitle.text.toString(), mEditTextNote.text.toString())
    }
}

class NoteHolder(view: View) : RecyclerView.ViewHolder(view) {
    val editTextTitle = view.findViewById<TextView>(R.id.single_note_edit_text_title)!!
    val editTextNote = view.findViewById<TextView>(R.id.single_note_edit_text_note)!!
}

class EditNoteHolder(view: View) : RecyclerView.ViewHolder(view) {
    val editTextTitle = view.findViewById<EditText>(R.id.single_note_edit_text_title)!!
    val editTextNote = view.findViewById<EditText>(R.id.single_note_edit_text_note)!!
}
