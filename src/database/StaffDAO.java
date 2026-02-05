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
                MedicalStaff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("Retrieved " + staffList.size() + " staff from database");

        } catch (SQLException e) {
            System.out.println(" Select failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public MedicalStaff getStaffById(int staffId) {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                MedicalStaff staff = extractStaffFromResultSet(resultSet);

                resultSet.close();
                statement.close();

                if (staff != null) {
                    System.out.println("Found staff with ID: " + staffId);
                }

                return staff;
            }
            System.out.println("No staff fond with ID: " + staffId);

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Select by ID failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return null;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'DOCTOR' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return doctors;

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MedicalStaff staff = extractStaffFromResultSet(resultSet);
                if (staff instanceof Doctor) {
                    doctors.add((Doctor) staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("Retrieved " + doctors.size() + " doctors");
        } catch (SQLException e) {
            System.out.println("Select doctors failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return doctors;
    }

    public List<Nurse> getAllNurses() {
        List<Nurse> nurses = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'NURSE' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return nurses;

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MedicalStaff staff = extractStaffFromResultSet(resultSet);
                if (staff instanceof Nurse) {
                    nurses.add((Nurse) staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("Retrieved " + nurses.size() + " nurses");
        } catch (SQLException e) {
            System.out.println("Select nurses failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return nurses;
    }

    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE staff SET name = ?, salary = ?, " +
                "experience_years = ?, specialization = ? " +
                "WHERE staff_id = ? AND staff_type = 'DOCTOR'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, doctor.getName());
            statement.setDouble(2, doctor.getSalary());
            statement.setInt(3, doctor.getExperienceYears());
            statement.setString(4, doctor.getSpecialization());
            statement.setInt(5, doctor.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println(" Doctor updated: " + doctor.getName());
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

    public boolean updateNurse(Nurse nurse) {
        String sql = "UPDATE staff SET name = ?, salary = ?, " +
                "experience_years = ?, patientsAssigned = ? " +
                "WHERE staff_id = ? AND staff_type = 'NURSE'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nurse.getName());
            statement.setDouble(2, nurse.getSalary());
            statement.setInt(3, nurse.getExperienceYears());
            statement.setInt(4, nurse.getPatientsAssigned());
            statement.setInt(5, nurse.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println(" Nurse updated: " + nurse.getName());
                return true;
            } else {
                System.out.println("⚠️ No nurse found with ID: " + nurse.getStaffId());
            }

        } catch (SQLException e) {
            System.out.println(" Update failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean deleteStaff(int staffId) {
        String sql = "DELETE FROM staff WHERE staff_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            if (rowsDeleted > 0) {
                System.out.println(" Staff deleted (ID: " + staffId + ")");
                return true;
            } else {
                System.out.println(" No staff found with ID: " + staffId);
            }

        } catch (SQLException e) {
            System.out.println(" Delete failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public List<MedicalStaff> searchByName(String name) {
        List<MedicalStaff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE name ILIKE ? ORDER BY name";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MedicalStaff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println(" Found " + staffList.size() + " staff");

        } catch (SQLException e) {
            System.out.println(" Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public List<MedicalStaff> searchBySalaryRange(double minSalary, double maxSalary) {
        List<MedicalStaff> staffList = new ArrayList<>();

        // BETWEEN includes both min and max (inclusive)
        String sql = "SELECT * FROM staff " +
                "WHERE salary BETWEEN ? AND ? " +
                "ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);
            statement.setDouble(2, maxSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MedicalStaff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println(" Found " + staffList.size() + " staff");

        } catch (SQLException e) {
            System.out.println(" Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public List<MedicalStaff> searchByMinSalary(double minSalary) {
        List<MedicalStaff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff " +
                "WHERE salary >= ? " +
                "ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MedicalStaff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    private MedicalStaff extractStaffFromResultSet(ResultSet resultSet) throws SQLException {
        int staffId = resultSet.getInt("staff_id");
        String name = resultSet.getString("name");
        double salary = resultSet.getDouble("salary");
        int experienceYears = resultSet.getInt("experience_years");
        String staffType = resultSet.getString("staff_type");

        MedicalStaff staff = null;

        if ("DOCTOR".equals(staffType)) {
            String specialization = resultSet.getString("specialization");
            staff = new Doctor(staffId, name, salary, experienceYears, specialization);

        } else if ("NURSE".equals(staffType)) {
            int patientsAssigned = resultSet.getInt("patientsAssigned");
            staff = new Nurse(staffId, name, salary, experienceYears, patientsAssigned);
        }

        return staff;
    }

    public void displayAllStaff() {
        List<MedicalStaff> staffList = getAllStaff();

        System.out.println("   ALL STAFF FROM DATABASE");

        if (staffList.isEmpty()) {
            System.out.println("No staff members in database.");
        } else {
            for (int i = 0; i < staffList.size(); i++) {
                MedicalStaff s = staffList.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getRole() + "] ");
                System.out.println(s.toString());
            }
        }
    }

    public void demonstratePolymorphism() {
        List<MedicalStaff> staffList = getAllStaff();

        System.out.println("  POLYMORPHISM: Staff from Database");

        if (staffList.isEmpty()) {
            System.out.println("No staff to demonstrate.");
        } else {
            for (MedicalStaff s : staffList) {
                s.work();
            }
        }
    }
}
