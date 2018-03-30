package com.andresarevalo.platzigram.login.presenter;

import com.andresarevalo.platzigram.login.interactor.LoginInteractor;
import com.andresarevalo.platzigram.login.interactor.LoginInteractorImpl;
import com.andresarevalo.platzigram.login.view.LoginView;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password) {
        loginView.disableInputs();
        loginView.showProgessBar();

        interactor.signIn(username,password);
    }

    @Override
    public void loginSuccess() {
        loginView.goPictures();
        loginView.hideProgessBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInputs();
        loginView.hideProgessBar();
        loginView.loginError(error);
    }
}
