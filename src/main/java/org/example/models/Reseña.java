package org.example.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Representa una reseña de un restaurante.
 */
@Data
@Entity
@Table(name = "Reseña", schema = "dbExamen")
public class Reseña {

    /**
     * El identificador único de la reseña.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El restaurante asociado con la reseña.
     */
    @ManyToOne
    @JoinColumn(name="restaurante_id", nullable = false)
    private Restaurante restaurante;

    /**
     * El usuario que escribió la reseña.
     */
    private String usuario;
    private int valoracion;
    private String comentario;

    @Override
    public String toString() {
        return "Reseña{" +
                "id=" + id +
                ", restaurante=" + (restaurante != null ? restaurante.getId() : "null") +
                ", usuario='" + usuario + '\'' +
                ", valoracion='" + valoracion + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
