package com.andresarevalo.platzigram.login.view;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andresarevalo.platzigram.R;
import com.andresarevalo.platzigram.login.presenter.LoginPresenter;
import com.andresarevalo.platzigram.login.presenter.LoginPresenterImpl;
import com.andresarevalo.platzigram.view.ContainerActivity;
import com.andresarevalo.platzigram.view.CreateAccountActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private TextInputEditText username, password;
    private Button login;
    private ProgressBar progressbarLogin;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        progressbarLogin = (ProgressBar) findViewById(R.id.progressbarLogin);
        hideProgessBar();

        presenter = new LoginPresenterImpl(this);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //if(username.equals(""))
                presenter.signIn(username.getText().toString(),
                                 password.getText().toString());
            }
        });
    }

    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgessBar() {
        progressbarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgessBar() {
        progressbarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this,getString(R.string.login_error) + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goCreateAccount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goPictures() {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }
}
