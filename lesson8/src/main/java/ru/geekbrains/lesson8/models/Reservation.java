package ru.geekbrains.lesson8.models;

import java.util.Date;
import java.util.Locale;

public class Reservation {

    public int getId() {
        return id;
    }

    public Table getTable() {
        return table;
    }

    private static int couter = 1000;
    private final int id;
    private Table table;
    private Date date;
    private String name;

    {
        id = ++couter;
    }

    public Reservation(Table table, Date date, String name) {
        this.table = table;
        this.date = date;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Резервирование #%d, столик № %d, " +
                "имя %s", id, table.getNo(), name);
    }
}