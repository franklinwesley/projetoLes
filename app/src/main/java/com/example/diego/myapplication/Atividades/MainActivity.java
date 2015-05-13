package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;

public class MainActivity extends Activity {


    DataBaseHelper db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        db = new DataBaseHelper(this);

    }

    public void onClick(View view){

        switch(view.getId()){
            case R.id.img_acompanhamento_semanal:
                Toast.makeText(this, "ACOMPANHAMENTO SEMANAL", Toast.LENGTH_SHORT).show();
                break;

            case R.id.img_historico:
                Toast.makeText(this, "HISTÓRICO", Toast.LENGTH_SHORT).show();
                break;

            case R.id.img_minhas_atividades:
                startActivity(new Intent(this, TarefasActivity.class));
                break;

            case R.id.img_conf:
                startActivity(new Intent(this, ConfiguracaoActivity.class));
                break;
        }
    }
}
