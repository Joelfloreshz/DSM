package sv.edu.udb.dsm.guia3.ejemplo_2_vc190544;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ejemplo_grid_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_grid_layout);
    }

    public void finalizarActividad(View v){
        finish();
    }
}