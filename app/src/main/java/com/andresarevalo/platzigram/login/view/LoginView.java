package com.andresarevalo.platzigram.login.view;

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
