package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Entidades.Tempo;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class HistoricoActivity extends Activity {


    private static final String[] prioridadesLista = new String[]{"Defina a Prioridade: ", "Baixa", "Média", "Alta"};
    private static final String[] categoriasLista = new String[]{"Defina a Categoria: ", "Lazer", "Trabalho"};

    public List<Atividade> atividades;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_layout);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        BDHelper bd = new BDHelper(this);
        db = bd.getBD(id);
        atividades = db.selecinarTodasAtividades();

        criarComponentes();

    }

    private void criarComponentes() {
        Spinner prioridade = (Spinner) findViewById(R.id.historico_prioridade);
        Spinner categoria = (Spinner) findViewById(R.id.historico_categoria);

        ArrayAdapter adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prioridadesLista);
        ArrayAdapter adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriasLista);

        adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        prioridade.setAdapter(adp);
        categoria.setAdapter(adp2);

        ListView view = (ListView) findViewById(R.id.historico_listview);
        List<String> strAtividades = new ArrayList<String>();
        DecimalFormat df = new DecimalFormat("#.00");
        for (Atividade atv : atividades){
            strAtividades.add(atv.toString() + "\nPorcentagem de tempo gasto: " + df.format((((float)atv.getTempo().totalMinutos())/getTempoTotal().totalMinutos())*100) + "%");
        }
        ArrayAdapter adp3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,strAtividades);
        view.setAdapter(adp3);

    }

    public Tempo getTempoTotal(){
        Tempo tempo = new Tempo();
        for (int i = 0; i < atividades.size(); i++) {
                tempo.setHora(tempo.getHora() + atividades.get(i).getTempo().getHora());
                tempo.setMinuto(tempo.getMinuto() + atividades.get(i).getTempo().getMinuto());

        }
        return tempo;
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_historico_voltar:
                finish();
        }
    }
}
