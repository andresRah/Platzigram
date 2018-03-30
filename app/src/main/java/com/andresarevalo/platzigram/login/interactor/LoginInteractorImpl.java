package com.andresarevalo.platzigram.login.interactor;

import com.andresarevalo.platzigram.login.presenter.LoginPresenter;
import com.andresarevalo.platzigram.login.repository.LoginRepository;
import com.andresarevalo.platzigram.login.repository.LoginRepositoryImpl;

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
    public void signIn(String username, String password) {
        repository.signIn(username, password);
    }
}
