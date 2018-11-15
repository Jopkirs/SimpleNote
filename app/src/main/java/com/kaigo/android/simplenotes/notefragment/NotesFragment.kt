package com.kaigo.android.simplenotes.notefragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kaigo.android.simplenotes.R
import java.util.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot





class NotesFragment : Fragment() {

    private lateinit var mRecyclerViewNotes: RecyclerView
    private val mCalendar = Calendar.getInstance()
    private val mArrayListNotes = arrayListOf<Any>()
    private lateinit var mButtonAddEditNoteObject: Button
    private lateinit var mButtonSaveNoteObject: Button

    private val mNoteRef = FirebaseDatabase.getInstance().reference.child("notes")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        //Add notes to arraylist from firebase
        val eventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val note = ds.value!!
                    mArrayListNotes.add(note)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        mNoteRef.addListenerForSingleValueEvent(eventListener)

        //RecyclerViewNotes
        mRecyclerViewNotes = view.findViewById(R.id.fragment_notes_recycler_view)
        val linearLayoutManager = LinearLayoutManager(activity)
        val notesAdapter = NotesAdapter(mArrayListNotes)
        mRecyclerViewNotes.apply {
            layoutManager = linearLayoutManager
            adapter = notesAdapter
        }

        //ButtonAddEditNote
        mButtonAddEditNoteObject = view.findViewById(R.id.button_add_note)
        mButtonAddEditNoteObject.setOnClickListener {
            mArrayListNotes.add(EditNoteObject())
            notesAdapter.notifyItemInserted(mArrayListNotes.lastIndex)

            mButtonAddEditNoteObject.visibility = View.INVISIBLE
            mButtonSaveNoteObject.visibility = View.VISIBLE
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("message")

            myRef.setValue("Hello, World!")
        }

        //ButtonSaveNote
        mButtonSaveNoteObject = view.findViewById(R.id.button_save_note)
        mButtonSaveNoteObject.setOnClickListener {
            val arrayListTitleAndNote = notesAdapter.getTitleAndNote()
            val newNote = Note()
            newNote.noteTitle = arrayListTitleAndNote[0]
            newNote.noteNote = arrayListTitleAndNote[1]
            newNote.noteDate = mCalendar.timeInMillis
            mArrayListNotes.removeAt(mArrayListNotes.lastIndex)
            mArrayListNotes.add(newNote)
            notesAdapter.notifyItemChanged(mArrayListNotes.lastIndex)

            mButtonAddEditNoteObject.visibility = View.VISIBLE
            mButtonSaveNoteObject.visibility = View.INVISIBLE

            mNoteRef.child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(newNote)
        }

        return view
    }
}