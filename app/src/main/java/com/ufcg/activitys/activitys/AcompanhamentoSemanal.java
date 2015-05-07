package com.ufcg.activitys.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.franklinwesley.les.R;
import com.ufcg.entities.Week;

public class AcompanhamentoSemanal extends ActionBarActivity {

    private Week week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acompanhamento_semanail);
        criarWeek();
        preencherTabela();

        TextView total = (TextView) findViewById(R.id.textView);
        total.setText(week.getTotalTime() + " horas");
    }

    private void preencherTabela() {
        TableLayout table = (TableLayout) findViewById(R.id.table);
        for (int i = 0; i < week.getAcivities().size(); i++) {
            TextView v = new TextView(this);
            Object[] k = week.getAcivities().keySet().toArray();
            v.setText(k[i] + "\t\t" + week.getAcivities().get(k[i]).toString() + "\t\t" + week.getSpecificTask((String)k[i]).getPriority());
            table.addView(v);
        }
    }

    private void criarWeek(){
        if (Week.getAllInstance().isEmpty()) {
            week = new Week();
        } else {
            week = Week.getAllInstance().get(Week.getAllInstance().size() - 1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acompanhamento_semanail, menu);
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
