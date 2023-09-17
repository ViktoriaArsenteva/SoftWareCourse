package ru.geekbrains.lesson4.task3;

public class CustomerRegistrationException extends RuntimeException{
    public CustomerRegistrationException() {
        super("Ошибка при регистрации клиента");
    }
}
