package com.andresarevalo.platzigram.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andresarevalo.platzigram.R;
import com.andresarevalo.platzigram.login.presenter.LoginPresenter;
import com.andresarevalo.platzigram.login.presenter.LoginPresenterImpl;
import com.andresarevalo.platzigram.view.ContainerActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private TextInputEditText username, password;
    private Button login;
    private ProgressBar progressbarLogin;
    private LoginPresenter presenter;

    private static final String TAG = "LoginRepositoryImpl";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener; // Estara pendiente de los cambios de sesión

    private LoginButton loginButtonFacebook;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser != null){ // El usuario esta logeado
                    Log.w(TAG,"Usario logeado OK " + firebaseUser.getEmail());
                    goPictures();
                }
                else{
                    Log.w(TAG,"Usario NO Logeado");
                }
            }
        };

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        loginButtonFacebook = (LoginButton) findViewById(R.id.login_facebook);

        progressbarLogin = (ProgressBar) findViewById(R.id.progressbarLogin);
        hideProgessBar();

        presenter = new LoginPresenterImpl(this);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //if(username.equals(""))
                signIn(username.getText().toString(),
                        password.getText().toString());

            }
        });

        loginButtonFacebook.setReadPermissions(Arrays.asList("email"));

        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Obtenemos los datos del usuario logeado
                Log.w(TAG, "Facebook Login success OK "+ loginResult.getAccessToken().getApplicationId());
                signInFacebookFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.w(TAG, "Facebook Login CANCELADO ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "Facebook Login ERROR "+ error.toString());
                error.printStackTrace();
            }
        });
    }

    private void signInFacebookFirebase(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = task.getResult().getUser();

                    SharedPreferences preferences
                            = getSharedPreferences("USER", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("email", user.getEmail());
                    editor.commit();

                   goPictures();
                   Toast.makeText(LoginActivity.this, "Login Facebook OK", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Facebook NOK", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void signIn(String username, String password) {
        presenter.signIn(username,
                password, this, firebaseAuth);
    }

    public void goCreateAccount(View view){
        goCreateAccount();
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

    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
