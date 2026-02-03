package database;

import model.Doctor;
import model.MedicalStaff;
import model.Nurse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class StaffDAO {
    public boolean insertDoctor(Doctor doctor) {
        String sql = "INSERT INTO staff (name, salary, experience_years, staff_type, specialization, patientsAssigned) " +
                "VALUES (?, ?, ?, 'DOCTOR', ?, NULL)";


        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, doctor.getName());
            statement.setDouble(2, doctor.getSalary());
            statement.setInt(3, doctor.getExperienceYears());
            statement.setString(4, doctor.getSpecialization());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("Doctor inserted: " + doctor.getName());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Insert failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean insertNurse(Nurse nurse) {
        String sql = "INSERT INTO staff (name, salary, experience_years, staff_type, specialization, patientsAssigned) " +
                "VALUES (?, ?, ?, 'NURSE', NULL, ?)";

        Connection connection = DatabaseConnection.getConnection();
        if(connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nurse.getName());
            statement.setDouble(2, nurse.getSalary());
            statement.setInt(3, nurse.getExperienceYears());
            statement.setInt(4, nurse.getPatientsAssigned());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("Nurse inserted: " + nurse.getName());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Insert Nurse failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public List<MedicalStaff> getAllStaff() {
        List<MedicalStaff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if(connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

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

    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE doctor SET name = ?, salary = ?, " +
                "experience_years = ?, specialization = ? " +
                "WHERE staff_id = ? AND staff_type = 'CHEF'";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, doctor.getName());
            statement.setDouble(2, doctor.getSalary());
            statement.setInt(3, doctor.getExperienceYears());
            statement.setString(4, doctor.getSpecialization());
            statement.setInt(5, doctor.getStaffId()); // WHERE condition
            int rowsUpdated = statement.executeUpdate();
            statement.close();
            if (rowsUpdated > 0) {
                System.out.println(" Chef updated: " + doctor.getName());
                return true;
            } else {
                System.out.println("⚠️ No doctor found with ID: " + doctor.getStaffId());
            }
        } catch (SQLException e) {
            System.out.println(" Update failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public Doctor getDoctorById(int doctorId) {
        String sql = "SELECT * FROM staff WHERE doctor_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, doctorId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Doctor doctor = extractDoctorFromRecultSet(resultSet);

                resultSet.close();
                statement.close();

                if (doctor != null) {
                    System.out.println("✅ Found staff with ID: " + doctorId);
                }

                return doctor;
            }

            System.out.println("⚠️ No doctor found with ID: " + doctorId);

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Select by ID failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return null;
    }

    private Doctor extractStaffFromResultSet(ResultSet resultSet) throws SQLException {
        int staffId = resultSet.getInt("staff_id");
        String name = resultSet.getString("name");
        double salary = resultSet.getDouble("salary");
        int experienceYears = resultSet.getInt("experience_years");
        String staffType = resultSet.getString("staff_type");

        Doctor staff = null;

        if ("CHEF".equals(staffType)) {
            String specialization = resultSet.getString("specialization");
            staff = new Doctor(staffId, name, salary, experienceYears, specialization);

        } else if ("WAITER".equals(staffType)) {
            int tablesServed = resultSet.getInt("tables_served");
            staff = new Waiter(staffId, name, salary, experienceYears, tablesServed);
        }

        return staff;
    }
}
