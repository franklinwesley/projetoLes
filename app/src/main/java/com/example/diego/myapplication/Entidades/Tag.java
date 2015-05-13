package com.example.diego.myapplication.Entidades;

public class Tag {

    String nome;
    String id;

    public Tag(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    public Tag() {
    }

    public Tag(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
