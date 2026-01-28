package database;

import model.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class StaffDAO {
    public void insertStaff(Doctor doctor) {
        String sql = "INSERT INTO public.doctor (name, salary, experience_years, specialization) VALUES (?, ?, ?, ?)";


        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return; // на случай если не подключилось

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, doctor.getName());
            statement.setDouble(2, doctor.getSalary());
            statement.setInt(3, doctor.getExperienceYears());
            statement.setString(4, doctor.getSpecialization());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Doctor inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Insert failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void getAllDoctors() {
        String sql = "SELECT * FROM doctor";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("\n--- ALL DOCTOR FROM DATABASE ---");
            while (resultSet.next()) {
                int id = resultSet.getInt("doctor_id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                int experience = resultSet.getInt("experience_years");
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Salary: " + salary);
                System.out.println("Experience: " + experience + " years");
                System.out.println("---");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(" Select failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }
}
