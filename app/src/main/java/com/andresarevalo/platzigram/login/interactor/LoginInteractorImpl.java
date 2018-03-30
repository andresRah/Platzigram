package com.andresarevalo.platzigram.login.interactor;

import android.app.Activity;

import com.andresarevalo.platzigram.login.presenter.LoginPresenter;
import com.andresarevalo.platzigram.login.repository.LoginRepository;
import com.andresarevalo.platzigram.login.repository.LoginRepositoryImpl;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        repository = new LoginRepositoryImpl(presenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        repository.signIn(username, password, activity, firebaseAuth);
    }
}
