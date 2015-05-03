package com.ufcg.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.example.franklinwesley.les.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClicked(View v){
        Intent i;
        if(v.getId() == R.id.btn_adicionar_atividade){
            i = new Intent(this, AdicionarTI.class);
            startActivity(i);
        } else if (v.getId() == R.id.btn_acompanhamento_semanal) {
            i = new Intent(this, AcompanhamentoSemanail.class);
            startActivity(i);
        } else if (v.getId() == R.id.btn_historico) {
            i = new Intent(this, Historico.class);
            startActivity(i);
        }
    }
}