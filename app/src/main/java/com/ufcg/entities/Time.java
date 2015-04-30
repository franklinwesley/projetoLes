package com.ufcg.entities;

/**
 * Created by ygorg_000 on 28/04/2015.
 *
 * Classe que representa o tempo gasto por um usuario realizando uma determinada tarefa.
 *
 */
public class Time implements Comparable {

    private int hours;
    private int minutes;

    /**
     *
     * @param hour - a quantidade de horas
     * @param minutes - a quantidade de minutos (entre 0 e 59)
     */
    public Time(int hour, int minutes){
        this.hours = hour;
        if(minutes < 60){
            this.minutes = minutes;
        }
        else{
            this.minutes=0;
        }
    }

    public int getHours(){
        return hours;
    }

    public int getMinutes(){
        return minutes;
    }

    public void setHours(int newHour){
        this.hours = newHour;
    }

    public void setMinutes(int newMinutes){
        if(minutes < 60){
            this.minutes = newMinutes;
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Time) {
            Time t = (Time) o;
            if (t.getHours() != getHours()) {
                if (getHours() > t.getHours()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                if (getMinutes() > t.getMinutes()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

    public void somarHoras(int hora, int minuto) {
        hours += hora;
        int m = minutes + minuto;
        if (m >= 60) {
            int h = m/60;
            hours += h;
            minutes = m%60;
        } else {
            minutes = m;
        }
    }
}