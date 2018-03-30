package com.andresarevalo.platzigram.login.repository;

import com.andresarevalo.platzigram.login.presenter.LoginPresenter;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public class LoginRepositoryImpl implements LoginRepository  {

    private LoginPresenter presenter;

    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(String username, String password) {

        boolean success = true;

        if(success){
           presenter.loginSuccess();
        }
        else{
           presenter.loginError("Error revisa tus datos");
        }
    }
}
