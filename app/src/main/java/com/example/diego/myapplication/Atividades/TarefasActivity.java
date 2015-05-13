package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.diego.myapplication.Adaptadores.TarefaAdapater;
import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;

import java.util.List;

public class TarefasActivity extends Activity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    public List<Atividade> atividades;
    private DataBaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tarefas_activity);

         db = new DataBaseHelper(this);

        atividades = db.selecinarTodasAtividades();

        recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
        registerForContextMenu(recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TarefaAdapater(this, atividades);
        recycler.setAdapter(adapter);

    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.fab:
                startActivity(new Intent(this, AdicionarTempoActivity.class));
                break;

        }
    }
}
