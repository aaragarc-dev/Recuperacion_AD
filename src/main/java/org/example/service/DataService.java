package org.example.service;

import org.example.models.Reseña;
import org.example.models.Restaurante;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Servicio de datos para gestionar operaciones con restaurantes y reseñas.
 */
public class DataService {

    private final SessionFactory sessionFactory;

    /**
     * Constructor del servicio de datos.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate
     */
    public DataService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Añadir un restaurante.
     *
     * @param restaurante el restaurante a añadir
     */
    public void addRestaurante(Restaurante restaurante) {
        sessionFactory.inTransaction(session -> session.persist(restaurante));
    }

    /**
     * Añadir una reseña a un restaurante específico.
     *
     * @param reseña el objeto reseña a añadir
     * @param restauranteId el ID del restaurante al que se añadirá la reseña
     */
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

    /**
     * Listar todos los restaurantes con baja valoración.
     *
     * @param valoracion la valoración máxima para filtrar restaurantes
     * @return una lista de restaurantes con valoración menor o igual a la especificada
     */
    public List<Restaurante> getRestauranteByValoracion(int valoracion) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT r FROM Restaurante r JOIN r.reseñas re WHERE re.valoracion <= 3", Restaurante.class)
                    .list();
        }
    }

    /**
     * Obtener un restaurante por su ID.
     *
     * @param id el ID del restaurante
     * @return el restaurante con el ID especificado, o null si no se encuentra
     */
    public Restaurante getRestauranteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Restaurante.class, id);
        }
    }

    /**
     * Obtener reseñas por un usuario específico.
     *
     * @param usuario el nombre del usuario
     * @return una lista de reseñas escritas por el usuario especificado
     */
    public List<Reseña> getReseñaByUsuario(String usuario) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reseña r WHERE r.usuario = :usuario", Reseña.class)
                    .setParameter("usuario", usuario)
                    .list();
        }
    }

    /**
     * Detectar usuarios sospechosos.
     *
     * @return una lista de nombres de usuarios que han escrito más de 3 reseñas con valoración menor o igual a 1
     */
    public List<String> getUsuariosSospechosos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT r.usuario FROM Reseña r WHERE r.valoracion <= 1 GROUP BY r.usuario HAVING COUNT(r) > 3", String.class)
                    .getResultList();
        }
    }

}