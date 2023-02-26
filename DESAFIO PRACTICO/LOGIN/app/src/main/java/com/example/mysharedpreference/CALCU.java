package com.example.mysharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CALCU extends AppCompatActivity {

    private EditText cuadratico;
    private EditText lineal;
    private EditText constante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcu);

        cuadratico=findViewById(R.id.cuadratico);
        lineal=findViewById(R.id.lineal);
        constante=findViewById(R.id.constante);
    }

    public void solve(View v){
        String r1,r2;
        String cuadra=cuadratico.getText().toString();
        String lin=lineal.getText().toString();
        String consta=constante.getText().toString();
        double a=Double.parseDouble(cuadra);
        double b=Double.parseDouble(lin);
        double c=Double.parseDouble(consta);
        if (!isImg(a,b,c)){
            r1=String.valueOf((-b+Math.sqrt(Math.pow(b,2)-4*a*c))/(2*a));
            r2=String.valueOf((-b-Math.sqrt(Math.pow(b,2)-4*a*c))/(2*a));
        } else{
            double sqr=Math.sqrt((Math.pow(b,2)-4*a*c)*-1);
            r1=-b/(2*a)+"+"+(sqr/(2*a))+"i";
            r2=-b/(2*a)+"-"+(sqr/(2*a))+"i";
        }

        Intent i=new Intent(this,Resultado.class);
        i.putExtra("txtRaiz1",r1);
        i.putExtra("txtRaiz2",r2);
        startActivity(i);
    }

    public boolean isImg(double a,double b,double c){
        double sqr=Math.pow(b,2)-4*a*c;
        if (sqr<0){
            return true;
        }
        else{
            return false;
        }
    }
}