package com.andresarevalo.platzigram.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public interface LoginInteractor {

    void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth);
}
