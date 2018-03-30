package com.andresarevalo.platzigram.login.view;

import android.content.Intent;
import android.view.View;

import com.andresarevalo.platzigram.view.ContainerActivity;
import com.andresarevalo.platzigram.view.CreateAccountActivity;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();

    void showProgessBar();
    void hideProgessBar();

    void loginError(String error);

    void goCreateAccount();
    void goPictures();
}
