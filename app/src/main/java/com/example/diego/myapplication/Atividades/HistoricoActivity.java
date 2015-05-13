package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.diego.myapplication.R;


public class HistoricoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_layout);
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_historico_voltar:
                finish();
        }
    }
}
