/**
 * Representa un restaurante.
 */
package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Restaurante {

    /**
     * El identificador único del restaurante.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El nombre del restaurante.
     */
    @Column(nullable = false, unique = true)
    private String nombre;

    /**
     * La ciudad donde se encuentra el restaurante.
     */
    @Column(nullable = false)
    private String ciudad;

    /**
     * Las reseñas asociadas con el restaurante.
     */
    @OneToMany(mappedBy = "restaurante", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Reseña> reseñas = new ArrayList<>();

    public Restaurante(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public Restaurante() {}

    @Override
    public String toString() {
        return "Restaurante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", opiniones=" + reseñas +
                '}';
    }
}