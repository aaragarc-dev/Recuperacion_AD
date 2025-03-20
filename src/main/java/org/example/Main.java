package org.example;

import org.example.models.Reseña;
import org.example.models.Restaurante;
import org.example.service.DataService;
import org.example.service.FileService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
/**
 * Clase principal para ejecutar las operaciones del servicio de datos y archivo.
 */
public class Main {
    public static void main(String[] args) {
        //Inicializar Hibernate
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        DataService dataService = new DataService(sessionFactory);
        FileService fileService = new FileService();

        //Importar restaurantes desde un archivo CSV
        String filePath = "src/main/restaurantes.csv";
        fileService.importRestaurantesFromCSV(filePath);

        //Agregar un restaurante manualmente
        Restaurante restaurante = new Restaurante("La Casa del Sabor", "Madrid");
        dataService.addRestaurante(restaurante);
        System.out.println("Restaurante agregado: " + restaurante);

        //Agregar una reseña a un restaurante
        Reseña reseña = new Reseña();
        reseña.setUsuario("carlos.gomez@example.com");
        reseña.setValoracion(5);
        reseña.setComentario("Excelente comida y servicio!");
        dataService.addReseña(reseña, restaurante.getId());
        System.out.println("Reseña agregada: " + reseña);

        //Obtener restaurantes con baja valoración
        List<Restaurante> restaurantesBajaValoracion = dataService.getRestauranteByValoracion(3);
        System.out.println("\n Restaurantes con baja valoración:");
        restaurantesBajaValoracion.forEach(System.out::println);

        //Buscar un restaurante por ID
        Restaurante restauranteBuscadoId = dataService.getRestauranteById(restaurante.getId());
        System.out.println("\n Restaurante encontrado por ID: " + restauranteBuscadoId);

        //Buscar reseñas de un usuario
        List<Reseña> reseñasUsuario = dataService.getReseñaByUsuario("carlos.gomez@example.com");
        System.out.println("\n Reseñas de carlos.gomez@example.com:");
        reseñasUsuario.forEach(System.out::println);

        //Detectar usuarios sospechosos (reseñas con puntuaciones bajas repetidas)
        List<String> usuariosSospechosos = dataService.getUsuariosSospechosos();
        System.out.println("\n Usuarios sospechosos:");
        usuariosSospechosos.forEach(System.out::println);

        //Cerrar la sesión de Hibernate
        sessionFactory.close();
    }
}
