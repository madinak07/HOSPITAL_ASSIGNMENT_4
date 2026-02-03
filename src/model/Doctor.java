package model;

public class Doctor extends MedicalStaff {

    private String specialization;

    public Doctor(int staffId, String name, double salary, int experienceYears, String specialization) {
        super(staffId, name, salary, experienceYears); // MUST be first
        setSpecialization(specialization);
    }

    public void setSpecialization(String specialization) {
        if(specialization==null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        this.specialization = specialization;
    }
    public String getSpecialization() {
        return specialization;
    }

    @Override
    public void work() {
        System.out.println("Doctor " + name + " is treating patients (" + specialization + ").");
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public void diagnosePatient() {
        System.out.println("Doctor " + name + " is diagnosing a patient.");
    }

    public boolean isSeniorDoctor() {
        return experienceYears >= 10;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: Doctor");
        System.out.println("Specialization: " + specialization);
        if (isSeniorDoctor()) {
            System.out.println("⭐ SENIOR DOCTOR");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Specialization: " + specialization;
    }
}


