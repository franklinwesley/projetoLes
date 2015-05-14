package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.diego.myapplication.Persistencia.BDHelper;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;


public class HistoricoActivity extends Activity {
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_layout);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        BDHelper bd = new BDHelper(this);
        db = bd.getBD(id);
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_historico_voltar:
                finish();
        }
    }
}
