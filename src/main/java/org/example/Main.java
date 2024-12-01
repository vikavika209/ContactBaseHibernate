package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        ContactDao contactDao = context.getBean(ContactDao.class);

        contactDao.saveContact(new Contact("Иванов Иван", "+79041655896", "ivanov@gmail.com"));
        contactDao.saveContact(new Contact("Петров Пётр", "+79051477528", "petrov@gmail.com"));
        List<Contact> contactList = contactDao.getAll();
        System.out.println(contactList);
    }
}