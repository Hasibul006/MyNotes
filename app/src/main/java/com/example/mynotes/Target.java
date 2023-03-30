package com.example.mynotes;

public class Target {
    String todo,value;

    public Target()
    {

    }

    public Target(String todo, String value) {
        this.todo = todo;
        this.value = value;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
