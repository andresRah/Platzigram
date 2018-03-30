package com.andresarevalo.platzigram.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public interface LoginPresenter {

    void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth); // Interactor
    void loginSuccess();
    void loginError(String error);
}
