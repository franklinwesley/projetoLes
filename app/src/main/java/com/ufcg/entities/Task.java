package com.ufcg.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ygorg_000 on 28/04/2015.
 *
 * A classe que representa uma tarefa realizada por um usuario
 */
public class Task implements Comparable {

    private static List<Task> allInstaces = new ArrayList<Task>();

    private String name;
    private Date date;
    private Time time;
    private Priority priority;

    /**
     *
     * @param name - nome da tarefa
     * @param time - tempo gasto nessa tarefa
     * @param priority - prioridade dessa tarefa
     * @param date - data em que a tarefa foi realizada
     */
    public Task (String name, Time time, Priority priority, Date date){
        this.name = name;
        this.time = time;
        this.priority = priority;
        this.date = date;
        allInstaces.add(this);
    }

    public static List<Task> getAllInstances() {
        return allInstaces;
    }

    public String getName(){
        return name;
    }

    public Time getTime(){
        return time;
    }

    public Date getDate(){
        return date;
    }

    public Priority getPriority(){
        return priority;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setTime(Time time){
        this.time = time;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setPriority(Priority priority){
        this.priority = priority;
    }

    /**
     * As tarefas serão rankeadas de Maior tempo gasto até Menor tempo gasto
     * @param
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if(o instanceof Task){
            return (((Task)o).getTime()).compareTo(this.getTime());
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}