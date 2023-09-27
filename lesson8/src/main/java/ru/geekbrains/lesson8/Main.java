package ru.geekbrains.lesson8;

import ru.geekbrains.lesson8.models.TableModel;
import ru.geekbrains.lesson8.presenters.BookingPresenter;
import ru.geekbrains.lesson8.presenters.Model;
import ru.geekbrains.lesson8.presenters.View;
import ru.geekbrains.lesson8.views.BookingView;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        View view = new BookingView();
        Model model = new TableModel();
        BookingPresenter presenter = new BookingPresenter(model, view);
        presenter.updateUIShowTables();
        presenter.updateUIShowReservations();

        view.reservationTable(new Date(), 2, "Николай");
        view.reservationTable(new Date(), 3, "Вадим");
        view.reservationTable(new Date(), 4, "Виктория");

        System.out.println();

        presenter.updateUIShowReservations();

        System.out.println();

        view.changeReservationTable(1001, new Date(), 1, "Евгений");

        System.out.println();

        presenter.updateUIShowReservations();

    }
}