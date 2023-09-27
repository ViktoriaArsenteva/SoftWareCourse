package ru.geekbrains.lesson8.presenters;

import ru.geekbrains.lesson8.models.Reservation;
import ru.geekbrains.lesson8.models.Table;

import java.util.Collection;
import java.util.Date;

public interface View {

    /**
     * Отображение списка столиков в приложении.
     * @param tables список столиков
     */
    void showTables(Collection<Table> tables);

    void showReservations(Collection<Reservation> reservations);

    /**
     * Отобразить результат бронирования столика
     * @param reservationNo номер брони
     */
    void showReservationTableResult(int reservationNo);

    /**
     * Установить наблюдателя.
     * @param observer наблюдатель
     */
    void setObserver(ViewObserver observer);

    /**
     * Клиент нажал на кнопку резерва столика
     * @param orderDate
     * @param tableNo
     * @param name
     */
    void reservationTable(Date orderDate, int tableNo, String name);

    /**
     * Клиент нажал на кнопку изменение резерва столика
     * @param orderDate
     * @param tableNo
     * @param name
     */
    void changeReservationTable(int oldReservation, Date reservationDate, int table,
                                String name);
}