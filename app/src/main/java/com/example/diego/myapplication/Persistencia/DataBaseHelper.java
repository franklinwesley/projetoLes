package com.example.diego.myapplication.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.diego.myapplication.Entidades.Data;
import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Entidades.Tempo;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String NOME = "projeto LES";
    private static final int VERSAO = 11;

    public DataBaseHelper(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tarefa (" +
                "nome TEXT PRIMARY KEY" +
                ");");

        db.execSQL("CREATE TABLE atividade (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hora INTEGER," +
                "minuto INTEGER," +
                "ano INTEGER," +
                "mes INTEGER," +
                "dia INTEGER," +
                "prioridade TEXT," +
                "categoria TEXT," +
                "nome_tarefa TEXT," +
                "FOREIGN KEY(nome_tarefa) REFERENCES tarefa(nome)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tarefa");
        db.execSQL("DROP TABLE atividade");
        onCreate(db);
    }

    public void inserirTarefa(String s){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("nome", s);

        db.insert("tarefa", null, contentValues);
        db.close();

    }

    public void inserirAtividade(Atividade atividade){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("hora", atividade.getTempo().getHora());
        contentValues.put("minuto", atividade.getTempo().getMinuto());
        contentValues.put("ano", atividade.getData().getAno());
        contentValues.put("mes", atividade.getData().getMes());
        contentValues.put("dia", atividade.getData().getDia());
        contentValues.put("prioridade", atividade.getPrioridade());
        contentValues.put("categoria", atividade.getCategoria());
        contentValues.put("nome_tarefa", atividade.getNome());

        db.insert("atividade", null, contentValues);
        db.close();

    }

    public List<String> selecionarNomesTodasTarefas(){

        SQLiteDatabase db = getReadableDatabase();

        List<String> tarefas = new ArrayList<String>();

        String selecionarTodos = "SELECT * FROM tarefa";

        Cursor cursor = db.rawQuery(selecionarTodos, null);

        if(cursor.moveToFirst()){

            do{

                String nome = new String();

                nome = cursor.getString(0);

                tarefas.add(nome);

            }while(cursor.moveToNext());
        }

        return tarefas;
    }

    public  List<Atividade> selecinarTodasAtividades(){

        SQLiteDatabase db = getReadableDatabase();

        List<Atividade> atividades = new ArrayList<Atividade>();

        String selecionarTodos = "SELECT * FROM atividade";

        Cursor cursor = db.rawQuery(selecionarTodos, null);

        if(cursor.moveToFirst()){

            do{

                Atividade atividade = new Atividade();
                Tempo tempo = new Tempo();
                Data data = new Data();

                tempo.setHora(cursor.getInt(0));
                tempo.setMinuto(cursor.getInt(1));
                data.setAno(cursor.getInt(2));
                data.setMes(cursor.getInt(3));
                data.setDia(cursor.getInt(4));
                atividade.setPrioridade(cursor.getString(6));
                atividade.setCategoria(cursor.getString(7));
                atividade.setNome(cursor.getString(8));

                atividades.add(atividade);

            }while(cursor.moveToNext());
        }

        return atividades;
    }

    public void removerAtividade(){

        SQLiteDatabase db = getWritableDatabase();

        String removerAtividades = "DELETE FROM atividade";

        db.execSQL(removerAtividades);

        db.close();
    }
}
