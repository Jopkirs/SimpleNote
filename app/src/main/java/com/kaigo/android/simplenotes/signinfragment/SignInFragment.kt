package com.kaigo.android.simplenotes.signinfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.kaigo.android.simplenotes.R

class SignInFragment : Fragment() {

    private lateinit var mButtonGoogleSignIn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        mButtonGoogleSignIn = view.findViewById(R.id.fragment_sign_in_button_sign_in)
        return view
    }

}