package com.example.diego.myapplication.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Entidades.Data;
import com.example.diego.myapplication.Entidades.Tempo;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String NOME = "projeto LES";
    private static final int VERSAO = 14;

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

        db.execSQL("CREATE TABLE tag (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "nome_tarefa TEXT," +
                "FOREIGN KEY(nome_tarefa) REFERENCES tarefa(nome)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tarefa");
        db.execSQL("DROP TABLE atividade");
        db.execSQL("DROP TABLE tag");
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

    public int atualizarAtividade(Atividade atividade){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        /**
         contentValues.put("hora", atividade.getTempo().getHora());
         contentValues.put("minuto", atividade.getTempo().getMinuto());
         contentValues.put("ano", atividade.getData().getAno());
         contentValues.put("mes", atividade.getData().getMes());
         contentValues.put("dia", atividade.getData().getDia());
         contentValues.put("prioridade", atividade.getPrioridade());
         contentValues.put("categoria", atividade.getCategoria());
         **/
        contentValues.put("nome_tarefa", atividade.getNome());

        String where = "_id = ?";

        String argumentos[] = { ""+atividade.getId() };

        return db.update("atividade", contentValues, where, argumentos);

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

                atividade.setId(cursor.getInt(0));
                tempo.setHora(cursor.getInt(1));
                tempo.setMinuto(cursor.getInt(2));
                data.setAno(cursor.getInt(3));
                data.setMes(cursor.getInt(4));
                data.setDia(cursor.getInt(5));
                atividade.setPrioridade(cursor.getString(6));
                atividade.setCategoria(cursor.getString(7));
                atividade.setNome(cursor.getString(8));

                atividade.setData(data);
                atividade.setTempo(tempo);

                atividades.add(atividade);

            }while(cursor.moveToNext());
        }

        return atividades;
    }

    public void removerTodasAtividade(){

        SQLiteDatabase db = getWritableDatabase();

        String removerAtividades = "DELETE FROM atividade";

        db.execSQL(removerAtividades);

        db.close();
    }

    public void removerAtividade(int id){

        SQLiteDatabase db = getWritableDatabase();

        String removerAtividades = "DELETE FROM atividade WHERE _id = "+id;

        db.execSQL(removerAtividades);

        db.close();
    }

    public void removerTarefas(){

        SQLiteDatabase db = getWritableDatabase();

        String removerTarefas = "DELETE FROM tarefa";

        db.execSQL(removerTarefas);

        db.close();

    }


    public void resetarDB(){
        removerTodasAtividade();
        removerTarefas();
    }
}
