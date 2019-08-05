package com.lester.alcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.lester.alcdemo.property.AddFragment;
import com.lester.alcdemo.property.PropertyList;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 1822;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(MainActivity.this);


            }
        });


        Button btn2 = findViewById(R.id.btn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AuthUI.getInstance().signOut(MainActivity.this);

                Intent i = new Intent(MainActivity.this, PropertyList.class);
                startActivity(i);
            }
        });


        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);


    }
}
