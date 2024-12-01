package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory(){
        var configuration = new Configuration()
                .addAnnotatedClass(Contact.class)
                .configure();
        return configuration.buildSessionFactory();
    }
}
