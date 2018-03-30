package com.andresarevalo.platzigram.login.presenter;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public interface LoginPresenter {

    void signIn(String username, String password); // Interactor
    void loginSuccess();
    void loginError(String error);
}
