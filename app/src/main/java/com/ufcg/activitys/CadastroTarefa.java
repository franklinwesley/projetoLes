package com.ufcg.activitys;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.franklinwesley.les.R;
import com.ufcg.entities.Priority;
import com.ufcg.entities.Task;

import java.util.ArrayList;
import java.util.List;


public class CadastroTarefa extends ActionBarActivity {

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
        EditText txt = (EditText) findViewById(R.id.edit_text);
        tarefa = txt.getText().toString();
    }
    public void onBtnClicked(View v){
        if(v.getId() == R.id.button2){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_tarefa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
