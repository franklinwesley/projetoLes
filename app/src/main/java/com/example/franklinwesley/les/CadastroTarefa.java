package com.example.franklinwesley.les;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class CadastroTarefa extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);

        List<String> tarefas = new ArrayList<String>();
        tarefas.add("oi");
        ListView lista = (ListView) findViewById(R.id.list);
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1, tarefas);
        lista.setAdapter(adaptador);
    }
    public void onBtnClicked(View v){
        if(v.getId() == R.id.button2){
            //cria tarefa
        } else if (v.getId() == R.id.button1) {
            this.finish();
        }
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
