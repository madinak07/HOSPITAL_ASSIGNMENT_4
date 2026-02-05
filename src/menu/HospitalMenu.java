package menu;

import database.*;
import model.*;
import Exception.InvalidInputException;
import java.util.List;
import java.util.Scanner;

public class HospitalMenu implements Menu {
    private Scanner scanner;
    private StaffDAO staffDAO;

    public HospitalMenu() {
        this.scanner = new Scanner(System.in);
        this.staffDAO = new StaffDAO();
    }


    @Override
    public void displayMenu(){
        System.out.println("\n========================================");
        System.out.println("     HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Doctor");
        System.out.println("2. Add Nurse");
        System.out.println("3. View All Staff");
        System.out.println("4. View Doctors");
        System.out.println("5. View Nurses");
        System.out.println("6. Update Staff");
        System.out.println("7. Delete Staff");
        System.out.println("8. Search by Name");
        System.out.println("9. Search by Salary Range");
        System.out.println("10. High-Paid Staff");
        System.out.println("11. Polymorphism Demo");
        System.out.println("0. Exit");
    }

    @Override
    public void run() {
        boolean running = true;

        while(running) {
            displayMenu();
            System.out.println("Enter choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: addDoctor(); break;
                    case 2: addNurse(); break;
                    case 3: viewAllStaff(); break;
                    case 4: viewDoctors(); break;
                    case 5: viewNurses(); break;
                    case 6: updateStaff(); break;
                    case 7: deleteStaff(); break;
                    case 8: searchByName(); break;
                    case 9: searchBySalaryRange(); break;
                    case 10: searchHighPaidStaff(); break;
                    case 11: demonstratePolymorphism(); break;
                    case 0: running=false;break;
                    default:
                        System.out.println("Invalid!");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void addDoctor() {
        try {
            System.out.println("id");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("name");
            String name = scanner.nextLine();

            System.out.println("salary");
            double salary = scanner.nextDouble();

            System.out.println("exp");
            int exp = scanner.nextInt();
            scanner.nextLine();

            System.out.println("specialization");
            String spec = scanner.nextLine();

            Doctor doctor = new Doctor(id, name, salary, exp, spec);
            staffDAO.insertDoctor(doctor);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Invalid input type!");
            scanner.nextLine();
        }
    }

    private void addNurse() {
        try {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Salary: ");
            double salary = scanner.nextDouble();

            System.out.print("Experience years: ");
            int exp = scanner.nextInt();

            System.out.print("Patients assigned: ");
            int patients = scanner.nextInt();

            Nurse nurse = new Nurse(id, name, salary, exp, patients);
            staffDAO.insertNurse(nurse);

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Invalid input type!");
            scanner.nextLine();
        }
    }


    private void viewAllStaff() {
            staffDAO.displayAllStaff();
    }


    private void viewDoctors() {
        List<Doctor> doctors = staffDAO.getAllDoctors();

        System.out.println("DOCTORS ONLY");

        if (doctors.isEmpty()) {
            System.out.println("No doctors in database");
        } else {
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                System.out.println((i + 1) + ". " + doctor.toString());
                if (doctor.isSeniorDoctor()) {
                    System.out.println("SENIOR DOCTOR");
                }
                System.out.println();
            }
            System.out.println("Total Doctors: " + doctors.size());
        }
    }

    private void viewNurses() {
            List<Nurse> nurses = staffDAO.getAllNurses();

            System.out.println("NURSES ONLY");

            if (nurses.isEmpty()) {
                System.out.println("No nurses in database");
            } else {
                for (int i = 0; i < nurses.size(); i++) {
                    Nurse nurse = nurses.get(i);
                    System.out.println((i + 1) + ". " + nurse.toString());
                    if (nurse.isHeadNurse()) {
                        System.out.println("HEAD NURSE");
                    }
                    System.out.println();
                }
                System.out.println("Total Nurses: " + nurses.size());
            }
    }

    private void updateStaff() {

        System.out.print("Enter Staff ID to update: ");

        try{
            int staffId = scanner.nextInt();
            scanner.nextLine();

            MedicalStaff existingStaff = staffDAO.getStaffById(staffId);

            if (existingStaff == null) {
                System.out.println("No staff found with ID: " + staffId);
                return;
            }

            System.out.println("Current Info:");
            System.out.println(existingStaff.toString());

            System.out.println("ENTER NEW VALUES");
            System.out.println("New name: " + existingStaff.getName());
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) {
                newName = existingStaff.getName();
            }

            System.out.print("New Salary: " + existingStaff.getSalary());
            String salaryInput = scanner.nextLine();
            double newSalary = salaryInput.trim().isEmpty() ?
                    existingStaff.getSalary() : Double.parseDouble(salaryInput);

            System.out.print("New Experience: " + existingStaff.getExperienceYears());
            String expInput = scanner.nextLine();
            int newExperience = expInput.trim().isEmpty() ?
                    existingStaff.getExperienceYears() : Integer.parseInt(expInput);

            if (existingStaff instanceof Doctor) {
                Doctor doctor = (Doctor) existingStaff;
                System.out.println("New Specialization: " + doctor.getSpecialization());
                String newSpec = scanner.nextLine();
                if (newSpec.trim().isEmpty()) {
                    newSpec = doctor.getSpecialization();
                }

                Doctor updatedDoctor = new Doctor(staffId, newName, newSalary, newExperience, newSpec);
                staffDAO.updateDoctor(updatedDoctor);
            } else if (existingStaff instanceof Nurse) {
                Nurse nurse = (Nurse) existingStaff;
                System.out.println("New Patients Assigned " + nurse.getPatientsAssigned());
                String patientInput = scanner.nextLine();
                int newPatient = patientInput.trim().isEmpty() ?
                        nurse.getPatientsAssigned() : Integer.parseInt(patientInput);

                Nurse updatedNurse = new Nurse(staffId, newName, newSalary, newExperience, newPatient);
                staffDAO.updateNurse(updatedNurse);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format!");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    private void deleteStaff() {
        System.out.println("\n┌─ DELETE STAFF ─────────────────────────┐");
        System.out.print("│ Enter Staff ID to delete: ");

        try {
            int staffId = scanner.nextInt();
            scanner.nextLine();

            // First, show who will be deleted
            MedicalStaff staff = staffDAO.getStaffById(staffId);

            if (staff == null) {
                System.out.println("❌ No staff found with ID: " + staffId);
                return;
            }

            System.out.println("Staff to delete:");
            System.out.println( staff.toString());

            System.out.print("⚠️  Are you sure? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                staffDAO.deleteStaff(staffId);
            } else {
                System.out.println("❌ Deletion cancelled.");
            }

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid input!");
            scanner.nextLine();
        }
    }

    private void searchByName() {;
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();

        List<MedicalStaff> results = staffDAO.searchByName(name);

        displaySearchResults(results, "Search: '" + name + "'");
    }

    private void searchBySalaryRange() {
        try {
            System.out.println("\n SEARCH BY SALARY RANGE");
            System.out.print("Enter minimum salary: ");
            double minSalary = scanner.nextDouble();

            System.out.print("Enter maximum salary: ");
            double maxSalary = scanner.nextDouble();
            scanner.nextLine();

            List<MedicalStaff> results = staffDAO.searchBySalaryRange(minSalary, maxSalary);

            displaySearchResults(results, "Salary: " + minSalary + " - " + maxSalary + " KZT");

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid number!");
            scanner.nextLine();
        }
    }

    private void searchHighPaidStaff() {
        try {
            System.out.print("Enter minimum salary: ");
            double minSalary = scanner.nextDouble();
            scanner.nextLine();

            List<MedicalStaff> results = staffDAO.searchByMinSalary(minSalary);

            displaySearchResults(results, "Salary >= " + minSalary + " KZT");

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid number!");
            scanner.nextLine();
        }
    }

    private void displaySearchResults(List<MedicalStaff> results, String criteria) {
        System.out.println("         SEARCH RESULTS              ");
        System.out.println("Criteria: " + criteria);

        if (results.isEmpty()) {
            System.out.println("📭 No staff found matching criteria.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                MedicalStaff s = results.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getRole() + "] ");
                System.out.println(s.toString());
            }
            System.out.println("Total Results: " + results.size());
        }
    }

    private void demonstratePolymorphism() {
        staffDAO.demonstratePolymorphism();
    }
}

