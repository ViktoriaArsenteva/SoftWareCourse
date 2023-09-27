package ru.geekbrains.lesson8.presenters;

import java.util.Collection;
import java.util.Date;

import ru.geekbrains.lesson8.models.Reservation;
import ru.geekbrains.lesson8.models.Table;

public interface Model {

    Collection<Table> loadTables();

    Collection<Reservation> loadReservations();

    int reservationTable(Date reservationDate, int tableNo, String name);

    int changeReservationTable(int oldReservation, Date reservationDate, int table,
                               String name);

}