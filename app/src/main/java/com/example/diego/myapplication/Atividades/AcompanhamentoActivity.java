package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.diego.myapplication.R;

public class AcompanhamentoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acompanhamento_layout);
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_acompanhamento_voltar:
                finish();
        }
    }
}
