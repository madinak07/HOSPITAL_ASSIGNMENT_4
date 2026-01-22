package model;

import javax.lang.model.element.Name;

public abstract class MedicalStaff {

    protected int staffId;
    protected String name;
    protected double salary;
    protected int experienceYears;

    public MedicalStaff(int staffId, String name, double salary, int experienceYears) {
        setStaffId(staffId);
        setName(name);
        setSalary(salary);
        setExperienceYears(experienceYears);
    }

    public int getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setName(String name) {
        if(name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        this.name=name;
    }

    public void setStaffId(int staffId) {
        if(staffId<0){
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        this.staffId = staffId;
    }

    public void setExperienceYears(int experienceYears) {
        if(experienceYears<0){
            throw new IllegalArgumentException("Years cannot be negative!");
        }
        this.experienceYears = experienceYears;
    }

    public void setSalary(double salary) {
        if(salary<0){
            throw new IllegalArgumentException("Salary cannot be negative!");
        }
        this.salary = salary;
    }

    public void displayInfo(){
        System.out.println(staffId + " " + name + " " + salary + "KZT "
        + experienceYears + "years");
    }

    public abstract void work();

    public abstract String getRole();

    public boolean isExperienced() {
        return experienceYears >= 5;
    }

    @Override
    public String toString() {
        return "[" + getRole() + "] " + name +
                " (ID: " + staffId +
                ", Salary: " + salary +
                ", Experience: " + experienceYears + " years)";
    }
}
