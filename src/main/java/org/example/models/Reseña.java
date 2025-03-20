package org.example.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Reseña", schema = "dbExamen")
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="restaurante_id", nullable = false)
    private Restaurante restaurante;

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
