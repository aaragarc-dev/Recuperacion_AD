package org.example;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase utilitaria para la gestión de la fábrica de sesiones de Hibernate.
 */
public class HibernateUtils {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure()
                .setProperty("hibernate.connection.password", System.getenv("MYSQL_ROOT_PASSWORD"))
                .setProperty("hibernate.connection.username", System.getenv("MYSQL_USER"))
                .buildSessionFactory();

    }
}


