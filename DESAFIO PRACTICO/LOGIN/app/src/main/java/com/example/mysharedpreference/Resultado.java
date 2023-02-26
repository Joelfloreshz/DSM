package com.example.mysharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {

    private TextView tvRaiz1;
    private TextView tvRaiz2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        tvRaiz1=findViewById(R.id.tv1);
        tvRaiz2=findViewById(R.id.tv2);

        Bundle bundle=getIntent().getExtras();
        String raiz1="Raiz 1: "+bundle.getString("txtRaiz1");
        String raiz2="Raiz 2: "+bundle.getString("txtRaiz2");

        tvRaiz1.setText(raiz1);
        tvRaiz2.setText(raiz2);
    }

    public void Finalizar(View v){
        Intent i=new Intent(this,HomeActivity.class);
        startActivity(i);
    }
}