package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;


import com.example.diego.myapplication.Entidades.Tag;
import com.example.diego.myapplication.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class EditarActivity extends Activity {

    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    private EditText edt_data;
    private EditText edt_tempo;
    private EditText edt_nome;

    private RadioButton rb_alta;
    private RadioButton rb_media;
    private RadioButton rb_baixa;
    private RadioButton rb_lazer;
    private RadioButton rb_trabalho;

    private Spinner tag;
    private String nome, prioridade, categoria;
    private int ano, mes, dia, hora, minuto;
    List<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.editar_atividade);

        edt_data = (EditText) findViewById(R.id.edt_editar_data);
        edt_tempo = (EditText) findViewById(R.id.edt_editar_tempo);
        edt_nome = (EditText) findViewById(R.id.edt_editarAtividade_nome);
        rb_alta = (RadioButton) findViewById(R.id.rb_editarAtividade_alta);
        rb_media = (RadioButton) findViewById(R.id.rb_editarAtividade_media);
        rb_baixa = (RadioButton) findViewById(R.id.rb_editarAtividade_baixa);
        rb_lazer = (RadioButton) findViewById(R.id.rb_editarAtividade_lazer);
        rb_trabalho = (RadioButton) findViewById(R.id.rb_editarAtividade_trabalho);

        Intent intent = getIntent();

        nome = intent.getExtras().getString("nome");
        ano = intent.getExtras().getInt("ano");
        mes = intent.getExtras().getInt("mes");
        dia = intent.getExtras().getInt("dia");
        hora = intent.getExtras().getInt("hora");
        minuto = intent.getExtras().getInt("minuto");
        prioridade = intent.getExtras().getString("prioridade");
        categoria = intent.getExtras().getString("categoria");

        if(prioridade.equals("Baixa")) {
            rb_baixa.setChecked(true);
        } else if(prioridade.equals("Media")){
            rb_media.setChecked(true);
        }else if(prioridade.equals("Alta")){
            rb_alta.setChecked(true);
        }

        if(categoria.equals("Lazer")){
            rb_lazer.setChecked(true);
        }else if(categoria.equals("Trabalho")){
            rb_trabalho.setChecked(true);
        }

        edt_nome.setText(nome);
        edt_data.setText(ano + ", " + mes + ", " + dia);
        edt_tempo.setText(hora + ":" + minuto);

        /**
        tags = new ArrayList<String>();
        tags.add(new Tag("lazer").toString());
        tags.add(new Tag("trabalho").toString());
        tags.add(new Tag("descanso").toString());
        tags.add("Nova Tag...");

        Spinner spn1 = (Spinner) findViewById(R.id.tag);

        ArrayAdapter<String> tagArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tags);
        ArrayAdapter<String> spinnerAdapter = tagArrayAdapter;

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spn1.setAdapter(spinnerAdapter);
         **/

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ano = year;
                mes = monthOfYear;
                dia = dayOfMonth;
                edt_data.setText(ano + ", " + mes + ", " + dia);
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hour, int minute) {
                hora = hour;
                minuto = minute;
                edt_tempo.setText(hora + ":" + minuto);
            }
        },newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);

        timePickerDialog.updateTime(0,0);

        /**

        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         **/
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.edt_editar_data:
                datePickerDialog.show();
                break;
            case R.id.edt_editar_tempo:
                timePickerDialog.show();
                break;
            case R.id.bt_editarAtividade_salvar:
                break;
            case R.id.bt_editarAtividade_cancelar:
                finish();
        }
    }
}
