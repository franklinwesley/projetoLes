package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.diego.myapplication.Auxiliares.LoginHelper;
import com.example.diego.myapplication.Auxiliares.NotificationHelper;
import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Entidades.Data;
import com.example.diego.myapplication.Persistencia.BDHelper;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;

import java.util.List;

public class MainActivity extends Activity {


    DataBaseHelper db;
    private String id;
    boolean notification = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        login();
        BDHelper bd = new BDHelper(this);
        db = bd.getBD(id);
        notification();
    }

    private void notification() {
        boolean tempoHoje = false;
        List<Atividade> t = db.selecinarTodasAtividades();
        for (int j = 0; j < t.size(); j++) {
            if (t.get(j).getData().equals(new Data())) {
                tempoHoje = true;
            }
        }
        if (!tempoHoje && notification) {
            sendBroadcast(new Intent(this, NotificationHelper.class));
        }
    }

    private void login() {
        Intent i = new Intent(this, LoginHelper.class);
        startActivityForResult(i, 1);
    }

    public void onClick(View view){
        Intent i;
        switch(view.getId()){

            case R.id.img_minhas_atividades:
                i = new Intent(this, TarefasActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.img_acompanhamento_semanal:
                i = new Intent(this, AcompanhamentoActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.img_historico:
                i = new Intent(this, HistoricoActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.img_conf:
                i = new Intent(this, ConfiguracaoActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.sing_out:
                LoginHelper.sair();
                finish();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && data.getExtras() != null) {
            switch (requestCode) {
                case 1:
                    id = data.getExtras().getString("id");
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
