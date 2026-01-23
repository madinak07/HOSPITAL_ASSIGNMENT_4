package menu;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class HospitalMenu implements Menu {
    private ArrayList<MedicalStaff> allStaff;
    private Scanner scanner;

    public HospitalMenu() {
        this.allStaff = new ArrayList<>();
        this.scanner = new Scanner(System.in);

        try {
            allStaff.add(new Doctor(3, "Madina", 850000, 8, "cardiosurgion"));
            allStaff.add(new Nurse(3, "Tolganayi", 360000, 1, 2));
        } catch (IllegalArgumentException e) {
            System.out.println("Error initializing test data: " + e.getMessage());
        }
    }
    @Override
    public void displayMenu(){
        System.out.println("\n========================================");
        System.out.println("     HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Doctor");
        System.out.println("2. Add Nurse");
        System.out.println("3. Staff work");
        System.out.println("4. View Doctors");
        System.out.println("5. View Nurses");
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
                    case 3: staffWork(); break;
                    case 4: viewDoctors(); break;
                    case 5: viewNurses(); break;
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
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Salary: ");
            double salary = scanner.nextDouble();

            System.out.print("Experience years: ");
            int exp = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Specialization: ");
            String spec = scanner.nextLine();

            MedicalStaff doctor = new Doctor(id, name, salary, exp, spec);
            allStaff.add(doctor);
            System.out.println("Doctor added!");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
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

            MedicalStaff nurse = new Nurse(id, name, salary, exp, patients);
            allStaff.add(nurse);
            System.out.println("model.Nurse added!");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }


    private void staffWork() {
        System.out.println("\n--- Staff work ---");
        for (MedicalStaff m : allStaff) {
            m.work();
        }
    }

    private void viewDoctors() {
        System.out.println("\n--- DOCTORS ONLY ---");
        for (MedicalStaff m : allStaff) {
            if (m instanceof Doctor d) {
                System.out.println(d);
            }
        }
    }

    private void viewNurses() {
        System.out.println("\"\\n--- NURSES ONLY ---\"");
        for (MedicalStaff m : allStaff) {
            if (m instanceof Nurse n) {
                System.out.println(n);
            }
        }
    }
}

