package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactDaoTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    ContactDao contactDao = context.getBean(ContactDao.class);

    @Test
    void getAll() {
        Contact contact = new Contact("Соколова Анастасия", "+79856245736", "sokolova@gmail.com");
        contactDao.saveContact(contact);
        List<Contact> contactList = contactDao.getAll();
        assertTrue(contactList.contains(contact));
    }

    @Test
    void getContact() {
        contactDao.saveContact(new Contact("Соколова Анастасия", "+79856245736", "sokolova@gmail.com"));
        Contact contact = contactDao.getContact(1L);
        assertEquals("Соколова Анастасия", contact.getName());
    }

    @Test
    void saveContact() {
        Contact contact = new Contact("Соколова Анастасия", "+79856245736", "sokolova@gmail.com");
        contactDao.saveContact(contact);
        List<Contact> contactList = contactDao.getAll();
        assertTrue(contactList.contains(contact));
    }

    @Test
    void updateContact() {
        Contact contact = new Contact("Соколова Анастасия", "+79856245736", "sokolova@gmail.com");
        contactDao.saveContact(contact);
        contactDao.updateContact(1L, "+77777777777");
        assertEquals("+77777777777", contactDao.getContact(1L).getPhoneNumber());
    }

    @Test
    void deleteContact() {
        Contact contact1 = new Contact("Соколова Анастасия", "+79856245736", "sokolova@gmail.com");
        Contact contact2 = new Contact("Иванов Иван", "+79041655896", "ivanov@gmail.com");
        Contact contact3 = new Contact("Петров Пётр", "+79051477528", "petrov@gmail.com");
        contactDao.saveContact(contact1);
        contactDao.saveContact(contact2);
        contactDao.saveContact(contact3);

        long idToDelete = contact2.getId();

        contactDao.deleteContact(2L);
        List<Contact> contactList = contactDao.getAll();

        assertFalse(contactList.stream().anyMatch(contact -> contact.getId() == idToDelete));
    }
}