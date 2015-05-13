package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;

/**
 * Created by Diego on 13/05/2015.
 */
public class ConfiguracaoActivity extends Activity {

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao_activity);

        db = new DataBaseHelper(this);
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_comecar_do_zero:
                db.resetarDB();
                break;
        }
    }
}
