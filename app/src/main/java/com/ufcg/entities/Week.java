package com.ufcg.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ygorg_000 on 28/04/2015.
 *
 * Classe que representa uma semana, nela estão contidas todas as tarefas que o usuario realizou durante a semana.
 */
public class Week {

    private static List<Week> allInstance = new ArrayList<Week>();

    private List<Task> activitiesInTheWeek;
    private Map< String, Time> activities;
    private Date initialDate;

    /**
     * Contrutor.
     */
    public Week(){
        activitiesInTheWeek = new ArrayList<Task>();
        activities = new HashMap<String, Time>();
        initialDate = new Date();
        allInstance.add(this);
    }

    public static List<Week> getAllInstance() {
        return allInstance;
    }

    /**
     * Adiciona uma nova tarefa a essa semana.
     * @param newTask - nova tarefa realizada pelo usuario.
     */
    public void addActivity(Task newTask){
        activitiesInTheWeek.add(newTask);
        if(activities.containsKey(newTask.getName())){
            Time tempo = sumTime(activities.get(newTask.getName()), newTask.getTime());
            activities.put(newTask.getName(),tempo);
        }else{
            activities.put(newTask.getName(), newTask.getTime());
        }
    }

    public Task getSpecificTask(String name){
        for (Task t : getActivitiesInTheWeek()){
            if(t.getName().equals(name)){
                return t;
            }
        }
        return null;
    }

    /**
     * Retorna o tempo total gasto em atividades na semana toda.
     * @return - o tempo total
     */
    public Time getTotalTime(){
        Time totalTime = new Time(0,0);
        for (Time time : activities.values()){
            totalTime = sumTime(totalTime, time);
        }
        return totalTime;
    }

    /* Soma dois tempos*/
    private Time sumTime(Time time1, Time time2){
        int totalHours = time1.getHours() + time2.getHours();
        totalHours += (time1.getMinutes() + time2.getMinutes())/59;
        int totalMinutes = (time1.getMinutes() + time2.getMinutes())%59;
        return new Time(totalHours, totalMinutes);
    }

    /**
     * Retorna o tempo total gasto em uma determinada tarefa durante a semana
     * @param task - Tarefa em que se deseja consultar o tempo gasto.
     * @return - o tempo gasto nessa tarefa em toda a semana.
     */
    public Time getActivityTime(String task){
        return activities.get(task);
    }

    public Date getInitialDate(){
        return initialDate;
    }

    public Map<String, Time> getAcivities(){
        return activities;
    }

    public List<Task> getActivitiesInTheWeek(){
        return activitiesInTheWeek;
    }


}
