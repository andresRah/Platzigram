package com.andresarevalo.platzigram;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by andresleonardoarevaloparra on 29/03/18.
 */

public class PlatzigramApplication extends Application {

    /*@Override
    public void onCreate() {
        super.onCreate();
    }*/
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;
    private String TAG = "PlatzigramApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //FirebaseCrash.log("Inicializando variables PlatzigramApplication");

        //FacebookSdk.sdkInitialize(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    //FirebaseCrash.logcat(Log.WARN, TAG, "Usuario logeado " + firebaseUser.getEmail());
                    Log.w(TAG, "Usuario logeado " + firebaseUser.getEmail());
                }else {
                    //FirebaseCrash.logcat(Log.WARN, TAG, "Usuario No logeado ");
                    Log.w(TAG, "Usuario No logeado ");
                }
            }
        };

        firebaseStorage = FirebaseStorage.getInstance();
    }


    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }
}

