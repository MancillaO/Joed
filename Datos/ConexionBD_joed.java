package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD_joed {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/aldebaran";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "";

    private static final String POSTGRESQL_URL = "jdbc:postgresql://localhost:5432/aldebaran";
    private static final String POSTGRESQL_USER = "postgres";
    private static final String POSTGRESQL_PASSWORD = "2801";

    public Connection getConnection(String dbType) throws SQLException {
        try {
            if (dbType.equalsIgnoreCase("mysql")) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver JDBC para MySQL
                return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            } else if (dbType.equalsIgnoreCase("postgresql")) {
                Class.forName("org.postgresql.Driver"); // Cargar el driver JDBC para PostgreSQL
                return DriverManager.getConnection(POSTGRESQL_URL, POSTGRESQL_USER, POSTGRESQL_PASSWORD);
            } else {
                throw new IllegalArgumentException("Tipo de base de datos no soportado");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("No se pudo cargar el driver JDBC", e);
        }
    }

    public void insertDueno(String nombre, String email) {
        try {
            // Insertar en MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    PreparedStatement stmt = mysqlConn
                            .prepareStatement("INSERT INTO dueno (nombre, email) VALUES (?, ?)")) {
                stmt.setString(1, nombre);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }

            // Insertar en PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    PreparedStatement stmt = postgresqlConn
                            .prepareStatement("INSERT INTO dueno (nombre, email) VALUES (?, ?)")) {
                stmt.setString(1, nombre);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }
            System.out.println("Dueño insertado correctamente en ambas bases de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listDuenos() {
        try {
            // Encabezado de la tabla
            System.out.println("+----+------------------+----------------------------+");
            System.out.println("| ID | Nombre           | Email                      |");
            System.out.println("+----+------------------+----------------------------+");

            // Listar de MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    Statement stmt = mysqlConn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM dueno")) {
                while (rs.next()) {
                    System.out.printf("| %-2d | %-16s | %-16s |\n",
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"));
                }
            }

            // Listar de PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    Statement stmt = postgresqlConn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM dueno")) {
                while (rs.next()) {
                    System.out.printf("| %-2d | %-16s | %-16s |\n",
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"));
                }
            }

            // Pie de la tabla
            System.out.println("+----+------------------+----------------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCoche(String marca, String modelo, String matricula, int duenoId) {
        try {
            // Insertar en MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    PreparedStatement stmt = mysqlConn.prepareStatement(
                            "INSERT INTO coche (marca, modelo, matricula, dueno_id) VALUES (?, ?, ?, ?)")) {
                stmt.setString(1, marca);
                stmt.setString(2, modelo);
                stmt.setString(3, matricula);
                stmt.setInt(4, duenoId);
                stmt.executeUpdate();
            }

            // Insertar en PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    PreparedStatement stmt = postgresqlConn.prepareStatement(
                            "INSERT INTO coche (marca, modelo, matricula, dueno_id) VALUES (?, ?, ?, ?)")) {
                stmt.setString(1, marca);
                stmt.setString(2, modelo);
                stmt.setString(3, matricula);
                stmt.setInt(4, duenoId);
                stmt.executeUpdate();
            }
            System.out.println("Coche insertado correctamente en ambas bases de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listCoches() {
        try {
            // Encabezado de la tabla
            System.out.println("+----+------------------+------------------+------------------+------------------+");
            System.out.println("| ID | Marca            | Modelo           | Matrícula        | Dueño            |");
            System.out.println("+----+------------------+------------------+------------------+------------------+");

            // Listar de MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    Statement stmt = mysqlConn.createStatement();
                    ResultSet rs = stmt.executeQuery(
                            "SELECT c.id, c.marca, c.modelo, c.matricula, d.nombre AS dueno_nombre " +
                                    "FROM coche c JOIN dueno d ON c.dueno_id = d.id")) {
                while (rs.next()) {
                    System.out.printf("| %-2d | %-16s | %-16s | %-16s | %-16s |\n",
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getString("matricula"),
                            rs.getString("dueno_nombre"));
                }
            }

            // Listar de PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    Statement stmt = postgresqlConn.createStatement();
                    ResultSet rs = stmt.executeQuery(
                            "SELECT c.id, c.marca, c.modelo, c.matricula, d.nombre AS dueno_nombre " +
                                    "FROM coche c JOIN dueno d ON c.dueno_id = d.id")) {
                while (rs.next()) {
                    System.out.printf("| %-2d | %-16s | %-16s | %-16s | %-16s |\n",
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getString("matricula"),
                            rs.getString("dueno_nombre"));
                }
            }

            // Pie de la tabla
            System.out.println("+----+------------------+------------------+------------------+------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDueno(int id) {
        try {
            // Eliminar coches asociados al dueño en MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    PreparedStatement stmt = mysqlConn.prepareStatement("DELETE FROM coche WHERE dueno_id = ?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Eliminar el dueño en MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    PreparedStatement stmt = mysqlConn.prepareStatement("DELETE FROM dueno WHERE id = ?")) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Dueño y coches asociados eliminados correctamente de MySQL.");
                } else {
                    System.out.println("No se encontró ningún dueño con ID " + id + " en MySQL.");
                }
            }

            // Eliminar coches asociados al dueño en PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    PreparedStatement stmt = postgresqlConn.prepareStatement("DELETE FROM coche WHERE dueno_id = ?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Eliminar el dueño en PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    PreparedStatement stmt = postgresqlConn.prepareStatement("DELETE FROM dueno WHERE id = ?")) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Dueño y coches asociados eliminados correctamente de PostgreSQL.");
                } else {
                    System.out.println("No se encontró ningún dueño con ID " + id + " en PostgreSQL.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCoche(int id) {
        try {
            // Eliminar coche en MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    PreparedStatement stmt = mysqlConn.prepareStatement("DELETE FROM coche WHERE id = ?")) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Coche eliminado correctamente de MySQL.");
                } else {
                    System.out.println("No se encontró ningún coche con ID " + id + " en MySQL.");
                }
            }

            // Eliminar coche en PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    PreparedStatement stmt = postgresqlConn.prepareStatement("DELETE FROM coche WHERE id = ?")) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Coche eliminado correctamente de PostgreSQL.");
                } else {
                    System.out.println("No se encontró ningún coche con ID " + id + " en PostgreSQL.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDueno(int id, String nuevoNombre, String nuevoEmail) {
        try {
            // Actualizar en MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    PreparedStatement stmt = mysqlConn.prepareStatement(
                            "UPDATE dueno SET nombre = ?, email = ? WHERE id = ?")) {
                stmt.setString(1, nuevoNombre);
                stmt.setString(2, nuevoEmail);
                stmt.setInt(3, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Dueño actualizado correctamente en MySQL.");
                } else {
                    System.out.println("No se encontró ningún dueño con ID " + id + " en MySQL.");
                }
            }

            // Actualizar en PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    PreparedStatement stmt = postgresqlConn.prepareStatement(
                            "UPDATE dueno SET nombre = ?, email = ? WHERE id = ?")) {
                stmt.setString(1, nuevoNombre);
                stmt.setString(2, nuevoEmail);
                stmt.setInt(3, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Dueño actualizado correctamente en PostgreSQL.");
                } else {
                    System.out.println("No se encontró ningún dueño con ID " + id + " en PostgreSQL.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el dueño:");
            e.printStackTrace();
        }
    }

    public void updateCoche(int id, String nuevaMarca, String nuevoModelo, String nuevaMatricula, int nuevoDuenoId) {
        try {
            // Actualizar en MySQL
            try (Connection mysqlConn = getConnection("mysql");
                    PreparedStatement stmt = mysqlConn.prepareStatement(
                            "UPDATE coche SET marca = ?, modelo = ?, matricula = ?, dueno_id = ? WHERE id = ?")) {
                stmt.setString(1, nuevaMarca);
                stmt.setString(2, nuevoModelo);
                stmt.setString(3, nuevaMatricula);
                stmt.setInt(4, nuevoDuenoId);
                stmt.setInt(5, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Coche actualizado correctamente en MySQL.");
                } else {
                    System.out.println("No se encontró ningún coche con ID " + id + " en MySQL.");
                }
            }

            // Actualizar en PostgreSQL
            try (Connection postgresqlConn = getConnection("postgresql");
                    PreparedStatement stmt = postgresqlConn.prepareStatement(
                            "UPDATE coche SET marca = ?, modelo = ?, matricula = ?, dueno_id = ? WHERE id = ?")) {
                stmt.setString(1, nuevaMarca);
                stmt.setString(2, nuevoModelo);
                stmt.setString(3, nuevaMatricula);
                stmt.setInt(4, nuevoDuenoId);
                stmt.setInt(5, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Coche actualizado correctamente en PostgreSQL.");
                } else {
                    System.out.println("No se encontró ningún coche con ID " + id + " en PostgreSQL.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el coche:");
            e.printStackTrace();
        }
    }
}