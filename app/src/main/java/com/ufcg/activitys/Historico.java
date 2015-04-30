package com.ufcg.activitys;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.franklinwesley.les.R;
import com.ufcg.entities.Priority;
import com.ufcg.entities.Task;
import com.ufcg.entities.Time;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Historico extends ActionBarActivity {

    private Map<String,Time> h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico);

        h = new HashMap<String,Time>();

        Task t = new Task("oi", new Time(1,50), Priority.Alta, new Date());
        Task t2 = new Task("ei", new Time(2,30), Priority.Alta, new Date());
        Task t3 = new Task("oi", new Time(1,59), Priority.Alta, new Date());

        List<Task> tasks = Task.getAllInstances();
        for (Task task: tasks) {
            if (h.containsKey(task.getName())){
                Time tarefa = h.get(task.getName());
                h.remove(task.getName());
                tarefa.somarHoras(task.getTime().getHours(), task.getTime().getMinutes());
                h.put(task.getName(),tarefa);
            } else {
                h.put(task.getName(),task.getTime());
            }
        }

        //mostrar todas as tarefas no map
        TableLayout table = (TableLayout) findViewById(R.id.table);
        for (int i = 0; i < h.size(); i++) {
            TextView v = new TextView(this);
            Object[] k = h.keySet().toArray();
            v.setText(k[i] + "\t\t" + h.get(k[i]).getHours() + ":" + h.get(k[i]).getMinutes());
            table.addView(v);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historico, menu);
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
