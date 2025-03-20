package org.example.service;

import org.example.models.Reseña;
import org.example.models.Restaurante;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DataService {

    private final SessionFactory sessionFactory;

    public DataService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //AÑADIR UN RESTAURANTE

    public void addRestaurante(Restaurante restaurante) {
        sessionFactory.inTransaction(session -> session.persist(restaurante));
    }

    //AÑADIR UNA RESEÑA A UN RESTAURANTE ESPECÍFICO

    public void addReseña(Reseña reseña, Integer restauranteId) {
        sessionFactory.inTransaction(session -> {
            Restaurante restaurante = session.get(Restaurante.class, restauranteId);
            if (restaurante == null) {
                throw new IllegalArgumentException("El restaurante con ID " + restauranteId + " no existe.");
            }
            reseña.setRestaurante(restaurante);
            session.persist(reseña);
        });
    }

    //  LISTAR TODOS LOS RESTAURANTES CON BAJA VALORACIÓN

    public List<Restaurante> getRestauranteByValoracion(int valoracion) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT r FROM Restaurante r JOIN r.reseñas re WHERE re.valoracion <= 3", Restaurante.class)
                    .list();
        }
    }

    //  OBTENER UN RESTAURANTE POR ID

    public Restaurante getRestauranteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Restaurante.class, id);
        }
    }

    // OBTENER UNA RESEÑA POR UN USUARIO ESPECÍFICO

    public List<Reseña> getReseñaByUsuario(String usuario) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reseña r WHERE r.usuario = :usuario", Reseña.class)
                    .setParameter("usuario", usuario)
                    .list();
        }
    }

    //DETECTAR USUARIOS SOSPECHOSOS

    public List<String> getUsuariosSospechosos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT r.usuario FROM Reseña r WHERE r.valoracion <= 1 GROUP BY r.usuario HAVING COUNT(r) > 3", String.class)
                    .getResultList();
        }
    }

}
