package model;

public class Nurse extends MedicalStaff {

    private int patientsAssigned;

    public Nurse(int staffId, String name, double salary, int experienceYears, int patientsAssigned) {
        super(staffId, name, salary, experienceYears);
        setPatientsAssigned(patientsAssigned);
    }

    public void setPatientsAssigned(int patientsAssigned) {
        if(patientsAssigned < 0) {
            throw new IllegalArgumentException("Patient assigned cannot be negative");
        }
        this.patientsAssigned = patientsAssigned;
    }

    public int getPatientsAssigned() {
        return patientsAssigned;
    }

    @Override
    public void work() {
        System.out.println("Nurse " + name + " is caring for patients.");
    }

    @Override
    public String getRole() {
        return "Nurse";
    }

    public void assistDoctor() {
        System.out.println("Nurse " + name + " is assisting a doctor.");
    }

    public boolean isHeadNurse() {
        return experienceYears >= 7 && patientsAssigned > 20;
    }

    @Override
    public String toString() {
        return super.toString() + " | Patients Assigned: " + patientsAssigned;
    }
}

