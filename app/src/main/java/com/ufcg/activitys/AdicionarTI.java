package com.ufcg.activitys;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.franklinwesley.les.R;
import com.ufcg.entities.Priority;
import com.ufcg.entities.Task;
import com.ufcg.entities.Time;
import com.ufcg.entities.Week;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AdicionarTI extends ActionBarActivity implements View.OnClickListener {

    private List<Task> tasks;
    private Week semana;

    private EditText toDateEtxt;
    private EditText toTimeEtxt;
    private DatePickerDialog toDatePickerDialog;
    private TimePickerDialog toTimePickerDialog;
    private SimpleDateFormat dateFormatter;
    Spinner tarefa;

    private String task;
    private int hora;
    private int minuto;
    private int ano;
    private int mes;
    private int dia;
    private Priority p;

    private List<String> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_ti);

        tasks = Task.getAllInstances();
        if (!Week.getAllInstance().isEmpty()) {
            semana = Week.getAllInstance().get(Week.getAllInstance().size()-1);
            if(!semana.isThisWeek(new Date())){
               semana = new Week();
            }
        } else {
            semana = new Week();
        }

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        tarefas = new ArrayList<String>();
        int tamanho;
        if (tasks.size() > 10) {
            tamanho = 10;
        } else {
            tamanho = tasks.size();
        }
        for (int i = 0; i < tamanho; i++) {
            if (!tarefas.contains(tasks.get(i).getName())) {
                tarefas.add(tasks.get(i).getName());
            }
        }
        tarefas.add("Adicionar nova Tarefa");
        tarefa = (Spinner) findViewById(R.id.tarefas);
        spinner(tarefa,tarefas);

        List<String> p = new ArrayList<String>();
        p.add(Priority.Alta.toString());
        p.add(Priority.Media.toString());
        p.add(Priority.Baixa.toString());
        Spinner prioridade = (Spinner) findViewById(R.id.prioridade);
        spinner(prioridade,p);
    }

    public void spinner(final Spinner spinner, List<String> tasks) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, tasks);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String tarefa = parent.getItemAtPosition(pos).toString();
                if (tarefa.equals("Adicionar nova Tarefa")) {
                    chamaTelaCadastro();
                } else if (tarefa.equals(Priority.Alta.toString())) {
                    p = Priority.Alta;
                } else if (tarefa.equals(Priority.Media.toString())) {
                    p = Priority.Media;
                } else if (tarefa.equals(Priority.Baixa.toString())) {
                    p = Priority.Baixa;
                } else {
                    task = tarefa;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                tarefas.add(0, data.getExtras().getString("task"));
                task = data.getExtras().getString("task");
                tarefa.setSelection(0);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void chamaTelaCadastro() {
        Intent i = new Intent(this, CadastroTarefa.class);
        startActivityForResult(i, 1);
    }

    public void onBtnClicked(View v){
        if(v.getId() == R.id.button){
            Task t = new Task(task, new Time(hora, minuto), p, new Date(ano,mes,dia));
            semana.addActivity(t);
            this.finish();
        } else if (v.getId() == R.id.button2) {
            this.finish();
        }
    }

    private void findViewsById() {
        Calendar newDate = Calendar.getInstance();
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
        toDateEtxt.setGravity(Gravity.CENTER);

        toTimeEtxt = (EditText) findViewById(R.id.etxt_totime);
        toTimeEtxt.setInputType(InputType.TYPE_NULL);
        toTimeEtxt.setText(new StringBuilder().append("00")
                .append(":").append("00"));
        toTimeEtxt.setGravity(Gravity.CENTER);
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private void setDateTimeField() {
        toDateEtxt.setOnClickListener(this);
        toTimeEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                ano = year;
                mes = monthOfYear;
                dia = dayOfMonth;
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(hour, minute);
                toTimeEtxt.setText(new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)));
                hora = hour;
                minuto = minute;
            }
        },newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);

        toTimePickerDialog.updateTime(0,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == toDateEtxt) {
            toDatePickerDialog.show();
        } else if(view == toTimeEtxt) {
            toTimePickerDialog.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
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