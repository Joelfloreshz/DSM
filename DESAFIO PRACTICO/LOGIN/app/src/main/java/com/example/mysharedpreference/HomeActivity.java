package com.example.mysharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {


    TextView textView_name, textView_email;
    Button button_logout, PD, DP;


  SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PD=(Button)findViewById(R.id.PD);
        PD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent( HomeActivity.this,CALCULADORA.class);
                startActivity(i);
            }
        });
        DP=(Button)findViewById(R.id.DP);
        DP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent( HomeActivity.this,CALCU.class);
                startActivity(i);
            }
        });

        textView_email = findViewById(R.id.text_email);
        textView_name = findViewById(R.id.text_fullname);
        button_logout = findViewById(R.id.button_logout);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        if (name != null || email != null){
            textView_name.setText("Nombre Completo - "+ name);
            textView_email.setText("Email -"+email);
        }

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Toast.makeText(HomeActivity.this, "Session Cerrada",Toast.LENGTH_SHORT).show();
            }
        });
    }
}