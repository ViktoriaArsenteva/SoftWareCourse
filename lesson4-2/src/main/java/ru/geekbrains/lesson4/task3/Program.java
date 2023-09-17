package ru.geekbrains.lesson4.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Program {

    public static void main(String[] args) throws CustomerRegistrationException{

        Core core = new Core();
        MobileApp mobileApp = new MobileApp(core.getTicketProvider(),
                core.getCustomerProvider());

        BusStation busStation = new BusStation(core.getTicketProvider());

        if (mobileApp.buyTicket("123456789012")) {
            System.out.printf("Клиент %d успешно купил билет \n",
                    mobileApp.getCustomer().getId());
            System.out.println();
            mobileApp.searchTicket(new Date());

            Collection<Ticket> tickets = core.getTicketProvider().getDatabase().getTickets();
            System.out.println("Список билетов: " + tickets);
            System.out.println();

            if (busStation.checkTicket(tickets.stream().findFirst().get()
                    .getQrcode())) {
                System.out.println("Клиент успешно прошел в автобус");
            }
        };

        System.out.println();

        MobileApp mobileApp1 = new MobileApp(core.getTicketProvider(),
                core.getCustomerProvider());
        mobileApp1.buyTicket("111116789012");
        System.out.printf("Клиент %d успешно купил билет \n",
                mobileApp1.getCustomer().getId());

        System.out.println();

        System.out.println("Список билетов: " + core.getTicketProvider().getDatabase().getTickets());

        System.out.println();
        System.out.println(core.getCustomerProvider().getCustomers());
    }

}

class Core {
    private final CustomerProvider customerProvider;
    private final TicketProvider ticketProvider;
    private final PaymentProvider paymentProvider;
    private final Database database;

    public Core() {
        database = new Database();
        customerProvider = new CustomerProvider(database);
        paymentProvider = new PaymentProvider();
        ticketProvider = new TicketProvider(database,paymentProvider);
    }

    public CustomerProvider getCustomerProvider() {
        return customerProvider;
    }

    public TicketProvider getTicketProvider() {
        return ticketProvider;
    }
}

/**
 * Покупатель
 */
class Customer {
    private static int counter = 0;
    private final int id;
    private Collection<Ticket> tickets;
    private String login;
    private String password;

    {
        id = ++counter;
    }

    public Customer() {
        tickets = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}

/**
 * Билет
 */
class Ticket {
    private static int counter = 1000;
    private final int id;
    private int customerId;
    private String date;
    private String qrcode;
    private boolean enable = true;

    {
        id = ++counter;
    }

    public Ticket(int customerId, String qrcode) {
        this.customerId = customerId;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.date = currentDate.format(formatter);
        this.qrcode = qrcode;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getDate() {
        return date;
    }

    public String getQrcode() {
        return qrcode;
    }

    public boolean isEnable() {
        return enable;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", date='" + date + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", enable=" + enable +
                '}';
    }
}

/**
 * База данных
 */
class Database {
    private static int counter = 100000000;
    private Collection<Ticket> tickets = new ArrayList<>();
    private Collection<Customer> customers = new ArrayList<>();

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Получит актуальную стоимость заказа
     * @return
     */
    public double getTicketAmount() {
        return 45;
    }

    public int createTicketOrder(int clientId) {
        return ++counter;
    }
}

class PaymentProvider {
    public Ticket buyTicket(int orderId, int customerId, String cardNo, double amount) {
        //TODO: Обращение к платежному шлюзу, попытка выполнить списание средств...
        Ticket ticket = new Ticket(customerId, "1111");
        return ticket;
    }
}

/**
 * Мобильное приложение
 */
class MobileApp {

    private final Customer customer;
    private final TicketProvider ticketProvider;
    private final CustomerProvider customerProvider;

    public Customer getCustomer() {
        return customer;
    }

    public MobileApp(TicketProvider ticketProvider, CustomerProvider customerProvider) {
        this.ticketProvider = ticketProvider;
        this.customerProvider = customerProvider;
        customer = customerProvider.getCustomer("<login>", "<password>");
    }

    public Collection<Ticket> getTickets() {
        return customer.getTickets();
    }

    public void searchTicket(Date date) {
        customer.setTickets(ticketProvider.searchTicket(customer.getId(), new Date()));
    }

    public boolean buyTicket(String cardNo) {
        return ticketProvider.buyTicket(customer.getId(), cardNo, customer.getTickets());
    }
}

class TicketProvider {
    private final Database database;
    private final PaymentProvider paymentProvider;

    public TicketProvider(Database database, PaymentProvider paymentProvider) {
        this.database = database;
        this.paymentProvider = paymentProvider;
    }

    public Database getDatabase() {
        return database;
    }

    public Collection<Ticket> searchTicket(int clientId, Date date) {
        Collection<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket: database.getTickets()) {
            if (ticket.getCustomerId() == clientId && ticket.getDate().equals(date)) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public boolean buyTicket(int clientId, String cardNo, Collection<Ticket> tickets) {
        int orderId = database.createTicketOrder(clientId);
        double amount = database.getTicketAmount();
        Ticket ticket = paymentProvider.buyTicket(orderId, clientId, cardNo, amount);
        tickets.add(ticket);
        database.getTickets().add(ticket);
        return true;
    }

    public boolean checkTicket(String qrcode) {
        for (Ticket ticket: database.getTickets()) {
            if (ticket.getQrcode().equals(qrcode)) {
                ticket.setEnable(false);
                // save database
                return true;
            }
        }
        return false;
    }
}


/**
 * Оператор работы с клиентом
 * @param dataBase базаданных, customers
 * @return регистрация клиента
 * @throws CustomerRegistrationException Исключение при регистрации клиента
 */
class CustomerProvider {
    private Database database;
    private Collection<Customer> customers = new ArrayList<>();

    public CustomerProvider(Database database) {
        this.database = database;
    }
    public Collection<Customer> getCustomers() {
        return database.getCustomers();
    }

    public Customer getCustomer(String login, String password) {

        // Предусловие
        if (login == "") {
            throw new CustomerRegistrationException();
        }

        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setPassword(password);
        database.addCustomer(customer);

        // Постусловие
        if (customer == null) {
            throw new CustomerRegistrationException();
        }

        return customer;
    }
}

/**
 * Автобусная станция
 */
class BusStation {
    private final TicketProvider ticketProvider;

    public BusStation(TicketProvider ticketProvider) {
        this.ticketProvider = ticketProvider;
    }

    public boolean checkTicket(String qrCode) {
        return ticketProvider.checkTicket(qrCode);
    }
}