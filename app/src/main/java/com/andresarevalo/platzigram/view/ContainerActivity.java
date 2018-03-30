package com.andresarevalo.platzigram.view;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andresarevalo.platzigram.R;
import com.andresarevalo.platzigram.login.view.CreateAccountActivity;
import com.andresarevalo.platzigram.login.view.LoginActivity;
import com.andresarevalo.platzigram.post.view.HomeFragment;
import com.andresarevalo.platzigram.view.fragment.ProfileFragment;
import com.andresarevalo.platzigram.view.fragment.SearchFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = "ContainerActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        firebaseInitialize();

        BottomBar bottombar = (BottomBar) findViewById(R.id.bottombar);

        bottombar.setDefaultTab(R.id.home);

        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;

                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;

                    case R.id.search:
                        SearchFragment searchFragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;
                }
            }
        });
    }

    private void firebaseInitialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser != null){ // El usuario esta logeado
                    Log.w(TAG,"Usario logeado" + firebaseUser.getEmail());
                }
                else{
                    Log.w(TAG,"Usario NO Logeado");
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.mSignOut:
                firebaseAuth.signOut();

                if(LoginManager.getInstance() != null){
                    LoginManager.getInstance().logOut();
                }
                Toast.makeText(this,"Sesión cerrada", Toast.LENGTH_SHORT);
                Intent i = new Intent(ContainerActivity.this, LoginActivity.class);

                startActivity(i);

                break;

            case R.id.mAbout:
                Toast.makeText(this,"platzigram by platzi", Toast.LENGTH_SHORT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
