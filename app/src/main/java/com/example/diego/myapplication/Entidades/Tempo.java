package com.example.diego.myapplication.Entidades;

public class Tempo {

    private int hora, minuto;

    public Tempo() {
    }

    public Tempo(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int totalMinutos(){
        return getHora()*60 + getMinuto();
    }

    @Override
    public String toString() {
        return  hora + " + " + minuto;
    }

    public String toTempo() {
        return hora + ":" + minuto;
    }
}
