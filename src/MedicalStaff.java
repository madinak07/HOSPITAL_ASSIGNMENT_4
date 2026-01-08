public class MedicalStaff {

    protected int staffId;
    protected String name;
    protected double salary;
    protected int experienceYears;

    public MedicalStaff(int staffId, String name, double salary, int experienceYears) {
        this.staffId = staffId;
        this.name = name;
        this.salary = salary;
        this.experienceYears = experienceYears;
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

    public void work() {
        System.out.println(name + " is working in the hospital.");
    }

    public String getRole() {
        return "Medical Staff";
    }

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
