package com.kaigo.android.simplenotes.notefragment

class Note {

    lateinit var noteTitle:String
    lateinit var noteNote: String
    var noteDate: Long = 0

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(title: String, note: String, time: Long) {
        this.noteTitle = title
        this.noteNote = note
        this.noteDate = time
    }

}
