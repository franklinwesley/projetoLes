package com.ufcg.activitys.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.franklinwesley.les.R;
import com.ufcg.entities.Task;

import java.util.ArrayList;
import java.util.List;


public class CadastroTarefa extends Activity{

    private String tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_tarefa);

        List<Task> t = Task.getAllInstances();
        List<String> tarefas = new ArrayList<String>();
        for (int i = 0; i < t.size(); i++) {
            tarefas.add(t.get(i).getName());
        }
        ListView lista = (ListView) findViewById(R.id.list);

        Spinner spinner = (Spinner) findViewById(R.id.tarefas);
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1, tarefas);
        lista.setAdapter(adaptador);
    }
    public void onBtnClicked(View v){
        if(v.getId() == R.id.button2){
            EditText txt = (EditText) findViewById(R.id.edit_text);
            tarefa = txt.getText().toString();
            onBackPressed();
            this.finish();
        } else if (v.getId() == R.id.button1) {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent();
        it.putExtra("task", tarefa);
        setResult(1, it);
        super.onBackPressed();
    }
}
