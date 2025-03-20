package org.example.service;

import org.example.models.Restaurante;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servicio para importar restaurantes desde un archivo CSV y guardarlos en la base de datos.
 */
public class FileService {

    /**
     * Importa restaurantes desde un archivo CSV.
     *
     * @param filePath la ruta del archivo CSV
     */
    public void importRestaurantesFromCSV(String filePath) {
        File file = new File(filePath);
        ArrayList<Restaurante> restaurantes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) { // Verificar que haya 2 valores (nombre, ciudad)
                    restaurantes.add(new Restaurante(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        saveToDatabase(restaurantes);
    }

    /**
     * Guarda una lista de restaurantes en la base de datos.
     *
     * @param restaurantes la lista de restaurantes a guardar
     */
    private void saveToDatabase(ArrayList<Restaurante> restaurantes) {
        String sql = "INSERT INTO Restaurante (nombre, ciudad) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbExamen", "root", "root");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (Restaurante r : restaurantes) {
                stmt.setString(1, r.getNombre());
                stmt.setString(2, r.getCiudad());
                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("Restaurantes importados y guardados en la base de datos.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}