package com.example.diego.myapplication.Entidades;

public class Atividade {

    private String nome;
    private Data data;
    private Tempo tempo;
    private String prioridade;
    private String categoria;

    public Atividade() {
    }

    public Atividade(String nome, Data data, Tempo tempo, String prioridade, String categoria) {
        this.nome = nome;
        this.prioridade = prioridade;
        this.data = data;
        this.tempo =  tempo;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Tempo getTempo() {
        return tempo;
    }

    public void setTempo(Tempo tempo) {
        this.tempo = tempo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
