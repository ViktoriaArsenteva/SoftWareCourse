package ru.geekbrains.lesson8.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import ru.geekbrains.lesson8.presenters.Model;

public class TableModel implements Model {

    private Collection<Table> tables;
    private Collection<Reservation> reservations = new ArrayList<>();

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Получение списка всех столиков
     * @return
     */
    @Override
    public Collection<Table> loadTables() {
        if (tables == null) {
            tables = new ArrayList<>();

            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());

        }
        return tables;
    }

    @Override
    public Collection<Reservation> loadReservations() {
        if (reservations != null && !reservations.isEmpty()) {
            return reservations;
        } else {
            System.out.println("Список брони пуст.");
            return null;
        }
    }

    /**
     * Бронирование столика
     * @param reservationDate Дата бронирования
     * @param tableNo Номер столика
     * @param name Имя
     * @return номер бронирования
     */
    @Override
    public int reservationTable(Date reservationDate, int tableNo, String name) {
        for (Table table: tables) {
            if (table.getNo() == tableNo) {
                Reservation reservation = new Reservation(table, reservationDate, name);
                table.getReservations().add(reservation);
                reservations.add(reservation);
                return reservation.getId();
            }
        }
        throw new RuntimeException("Ошибка бронирования столика. Повторите попытку позже.");
    }

    public int changeReservationTable(int oldReservation, Date reservationDate, int newTable,
                                      String name) {
        for (Reservation reservation: reservations) {
            if (reservation.getId() == oldReservation) {
                for (Table table: tables) {
                    if (table.getNo() == reservation.getTable().getNo()) {
                        table.getReservations().remove(reservation);
                    }
                }
                reservations.remove(reservation);
                for (Table table: tables) {
                    if (table.getNo() == newTable) {
                        Reservation newReservation = new Reservation(table, reservationDate,
                                name);
                        table.getReservations().add(reservation);
                        reservations.add(newReservation);
                        return newReservation.getId();
                    }
                }
            }
        }
        throw new RuntimeException("Ошибка бронирования столика. Повторите попытку позже.");
    }
}