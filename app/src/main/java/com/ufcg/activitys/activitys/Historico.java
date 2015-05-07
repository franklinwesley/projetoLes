package com.ufcg.activitys.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.franklinwesley.les.R;
import com.ufcg.entities.Time;
import com.ufcg.entities.Week;

import java.util.List;
import java.util.Map;

public class Historico extends ActionBarActivity {

    private List<Week> weeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico);

        weeks = Week.getAllInstance();
        if (!weeks.isEmpty()) {
            preencherTabelas();
        }
    }

    private void preencherTabelas() {
        // semana atual
        TableLayout atual = (TableLayout) findViewById(R.id.table);
        TableLayout passada = (TableLayout) findViewById(R.id.table2);
        TableLayout anteriorPassada = (TableLayout) findViewById(R.id.table3);
        table(atual,1);
        if (Week.getAllInstance().size() >= 3) {
            table(passada,2);
            table(anteriorPassada,3);
        } else if (Week.getAllInstance().size() == 2) {
            table(passada,2);
        }
    }

    private void table(TableLayout table, int indice) {
        Week semana = weeks.get(weeks.size() - indice);
        Map <String,Time> atividadesSemana = semana.getAcivities();
        for (int i = 0; i < atividadesSemana.size(); i++) {
            TextView v = new TextView(this);
            Object[] k = atividadesSemana.keySet().toArray();
            v.setText(k[i] + "\t\t" + semana.getAcivities().get(k[i]).toString() + "\t\t" + semana.getSpecificTask((String)k[i]).getPriority());
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
