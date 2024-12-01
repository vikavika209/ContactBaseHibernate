package org.example;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class ContactDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Contact> getAll(){
        try(var session = sessionFactory.openSession()){
            return session.createQuery("SELECT c from Contact c", Contact.class).getResultList();
        }
    }

    public Contact getContact(long id){
        try(var session = sessionFactory.openSession()){
            return session.get(Contact.class, id);
        }
    }

    public long saveContact(Contact contact) {
        try(var session = sessionFactory.openSession()){
            var transaction = session.beginTransaction();
            var contactId = (long) session.save(contact);
            transaction.commit();
            return contactId;
        }
    }

    public void updateContact(long id, String param) {

        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                var contact = session.get(Contact.class, id);
                if (contact == null) {
                    throw new EntityNotFoundException("Contact with ID " + id + " not found");
                }
                if (isPhoneNumber(param)) {
                    contact.setPhoneNumber(param);
                } else if (isEmail(param)) {
                    contact.setEmail(param);
                } else {
                    throw new IllegalArgumentException("Invalid parameter format");
                }
                transaction.commit();
            }catch (Exception e){
                throw new RuntimeException("Произошла ошибка", e);
            }
        }
    }
    public void deleteContact(long id){
        try(var session = sessionFactory.openSession()){
            var transaction = session.beginTransaction();
            var contact = getContact(id);
            if(contact != null){
                session.delete(contact);
            }
            transaction.commit();
        }
    }

    private boolean isPhoneNumber(String line){
        String phoneNumberRegex = "^\\+?[0-9\\-]{7,15}$";
        return line.matches(phoneNumberRegex);
    }

    private boolean isEmail(String line) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+.-]+$";
        return line.matches(emailRegex);
    }

}
