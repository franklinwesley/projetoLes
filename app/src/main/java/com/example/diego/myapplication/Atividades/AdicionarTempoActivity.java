package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.diego.myapplication.Entidades.Data;
import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Entidades.Tempo;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdicionarTempoActivity extends Activity {

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    Spinner sp_tarefas;

    EditText edt_data;
    EditText edt_tempo;

    RadioGroup rg;

    RadioButton rb_alta;
    RadioButton rb_media;
    RadioButton rb_baixa;

    RadioButton rb_lazer;
    RadioButton rb_trabalho;

    DataBaseHelper db;

    List<String> nome_tarefas;

    Atividade atividade;
    Data data;
    Tempo tempo;

    int ano, mes, dia, hora, minuto;

    String prioridade;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_tempo);

        db = new DataBaseHelper(this);

        nome_tarefas = new ArrayList<String>();

        nome_tarefas = db.selecionarNomesTodasTarefas();

        nome_tarefas.add("Nova");

        prioridade = "Alta";
        categoria = "Lazer";

        rg = (RadioGroup) findViewById(R.id.rg_adicionarTarefa_prioridade);
        sp_tarefas = (Spinner) findViewById(R.id.sp_adicionarTempo_tarefas);
        edt_data = (EditText) findViewById(R.id.edt_adicionarTempo_data);
        edt_tempo = (EditText) findViewById(R.id.edt_adicionarTempo_tempo);
        rb_alta = (RadioButton) findViewById(R.id.rb_adicionarTempo_alta);
        rb_media = (RadioButton) findViewById(R.id.rb_adicionarTempo_media);
        rb_baixa = (RadioButton) findViewById(R.id.rb_adicionarTempo_baixa);
        rb_lazer = (RadioButton) findViewById(R.id.rb_adicionarTempo_lazer);
        rb_trabalho = (RadioButton) findViewById(R.id.rb_adicionarTempo_trabalho);

        data = new Data(0,0,0);
        tempo = new Tempo(0,0);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edt_data.setText(year + ", " + monthOfYear + ", " + dayOfMonth);
                ano = year;
                mes = monthOfYear;
                dia = dayOfMonth;
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hour, int minute) {
                edt_tempo.setText(hour + ":" + minute);
                hora = hour;
                minuto = minute;
            }
        },newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);

        ArrayAdapter<String> tagArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nome_tarefas);
        ArrayAdapter<String> spinnerAdapter = tagArrayAdapter;

        sp_tarefas.setAdapter(spinnerAdapter);

        sp_tarefas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(nome_tarefas.get(position).equals("Nova")){

                    LayoutInflater li = LayoutInflater.from(parent.getContext());
                    View dialog = li.inflate(R.layout.dialog_edittext, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent.getContext());

                    alertDialogBuilder.setView(dialog);

                    final EditText userInput = (EditText) dialog
                            .findViewById(R.id.editTextDialogUserInput);

                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            String nova_tarefa = userInput.getText().toString();
                            nome_tarefas.add(nova_tarefa);
                            db.inserirTarefa(nova_tarefa);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.edt_adicionarTempo_data:
                datePickerDialog.show();
                break;
            case R.id.edt_adicionarTempo_tempo:
                timePickerDialog.show();
                break;
            case R.id.btn_adicionarTempo_salvar:
                atividade = new Atividade(sp_tarefas.getSelectedItem().toString(), data, tempo, prioridade, categoria);
                db.inserirAtividade(atividade);
                db.close();
            case R.id.btn_adicionarTempo_cancelar:
                finish();
            case R.id.rb_adicionarTempo_alta:
                prioridade = "Alta";
                break;
            case R.id.rb_adicionarTempo_media:
                prioridade = "Media";
                break;
            case R.id.rb_adicionarTempo_baixa:
                prioridade = "Baixa";
                break;
            case R.id.rb_adicionarTempo_lazer:
                categoria = "Lazer";
                break;
            case R.id.rb_adicionarTempo_trabalho:
                prioridade = "Trabalho";
                break;

        }
    }
}
