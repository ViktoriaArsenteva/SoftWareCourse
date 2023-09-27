package ru.geekbrains.lesson8.views;

import ru.geekbrains.lesson8.models.Reservation;
import ru.geekbrains.lesson8.models.Table;
import ru.geekbrains.lesson8.presenters.View;
import ru.geekbrains.lesson8.presenters.ViewObserver;

import java.util.Collection;
import java.util.Date;

public class BookingView implements View {

    private ViewObserver observer;

    public void setObserver(ViewObserver observer) {
        this.observer = observer;
    }

    public void showTables(Collection<Table> tables) {
        for (Table table: tables) {
            System.out.println(table);
        }
    }

    @Override
    public void showReservations(Collection<Reservation> reservations) {
        if (reservations != null) {
            for (Reservation reservation: reservations) {
                System.out.println(reservation);
            }
        }
    }

    @Override
    public void showReservationTableResult(int reservationNo) {
        if (reservationNo > 0) {
            System.out.printf("Столик успешно забронирован. Номер вашей брони: #%d\n",
                    reservationNo);
        } else {
            System.out.println("Что-то пошло не так, попробуйте повторить попытку позже.");
        }
    }

    /**
     * Действие клиента (пользователь нажал на кнопку бронирования), бронирование столика
     * @param orderDate дата бронирования
     * @param tableNo номер столика
     * @param name Имя
     */
    public void reservationTable(Date orderDate, int tableNo, String name) {
        if (observer != null) {
            observer.onReservationTable(orderDate, tableNo, name);
        }
    }

    /**
     * Действие клиента (пользователь нажал на кнопку изменения резерва)
     * @param oldReservation
     * @param reservationDate
     * @param table
     * @param name
     */
    public void changeReservationTable(int oldReservation, Date reservationDate, int table,
                                       String name) {
        if (observer != null) {
            observer.onChangeReservationTable(oldReservation, reservationDate, table, name);
        }
    }
}