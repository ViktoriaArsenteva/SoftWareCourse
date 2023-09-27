package ru.geekbrains.lesson8.presenters;

import java.util.Collection;
import java.util.Date;

import ru.geekbrains.lesson8.models.Reservation;
import ru.geekbrains.lesson8.models.Table;

public class BookingPresenter implements ViewObserver{

    private final Model model;
    private final View view;

    public BookingPresenter(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.setObserver(this);
    }

    /**
     * Получение списка столиков
     * @return Коллекция типа Table
     */
    public Collection<Table> loadTables() {
        return model.loadTables();
    }

    /**
     * Получение списка броней
     * @return Коллекция типа Table
     */
    public Collection<Reservation> loadReservations() {
        return model.loadReservations();
    }

    /**
     * Отображение списка столиков (на отображении)
     */
    public void updateUIShowTables() {
        view.showTables(loadTables());
    }

    /**
     * Отображение списка броней
     */
    public void updateUIShowReservations(){
        view.showReservations(loadReservations());
    }

    public void updateUIReservationTableResult(int reservationNo) {
        view.showReservationTableResult(reservationNo);
    }

    /**
     * Произошло событие пользователь ннажал на кнопку резерва столика
     * @param orderDate
     * @param tableNo
     * @param name
     */
    @Override
    public void onReservationTable(Date orderDate, int tableNo, String name) {
        try {
            int reservationNo = model.reservationTable(orderDate, tableNo, name);
            updateUIReservationTableResult(reservationNo);
        }
        catch (RuntimeException e) {
            updateUIReservationTableResult(-1);
        }
    }

    @Override
    public void onChangeReservationTable(int oldReservation, Date reservationDate, int table, String name) {
        try {
            int reservationNo = model.changeReservationTable(oldReservation, reservationDate,
                    table, name);
            updateUIReservationTableResult(reservationNo);
        }
        catch (RuntimeException e) {
            updateUIReservationTableResult(-1);
        }
    }
}