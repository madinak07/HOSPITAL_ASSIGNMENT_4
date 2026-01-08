import java.util.ArrayList;
import java.util.Scanner;

class HospitalManagementSystem {

    private static ArrayList<MedicalStaff> staffList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("     HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Medical Staff (General)");
            System.out.println("2. Add Doctor");
            System.out.println("3. Add Nurse");
            System.out.println("4. View All Staff (Polymorphic)");
            System.out.println("5. Demonstrate Polymorphism");
            System.out.println("6. View Doctors Only");
            System.out.println("7. View Nurses Only");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addMedicalStaff();
                case 2 -> addDoctor();
                case 3 -> addNurse();
                case 4 -> viewAllStaff();
                case 5 -> demonstratePolymorphism();
                case 6 -> viewDoctors();
                case 7 -> viewNurses();
                case 0 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    // ===== MENU METHODS =====

    private static void addMedicalStaff() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Salary: ");
        double salary = scanner.nextDouble();

        System.out.print("Experience years: ");
        int exp = scanner.nextInt();

        staffList.add(new MedicalStaff(id, name, salary, exp));
        System.out.println("Medical staff added!");
    }

    private static void addDoctor() {
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
        staffList.add(doctor);
        System.out.println("Doctor added!");
    }

    private static void addNurse() {
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
        staffList.add(nurse);
        System.out.println("Nurse added!");
    }

    private static void viewAllStaff() {
        System.out.println("\n--- ALL MEDICAL STAFF ---");

        for (MedicalStaff m : staffList) {
            System.out.println(m);

            if (m instanceof Doctor d && d.isSeniorDoctor()) {
                System.out.println("   Senior Doctor ⭐");
            }

            if (m instanceof Nurse n && n.isHeadNurse()) {
                System.out.println("   Head Nurse ⭐");
            }
        }
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n--- POLYMORPHISM DEMO ---");
        for (MedicalStaff m : staffList) {
            m.work(); // SAME METHOD, DIFFERENT BEHAVIOR
        }
    }

    private static void viewDoctors() {
        System.out.println("\n--- DOCTORS ONLY ---");
        for (MedicalStaff m : staffList) {
            if (m instanceof Doctor d) {
                System.out.println(d);
            }
        }
    }

    private static void viewNurses() {
        System.out.println("\n--- NURSES ONLY ---");
        for (MedicalStaff m : staffList) {
            if (m instanceof Nurse n) {
                System.out.println(n);
            }
        }
    }
}

