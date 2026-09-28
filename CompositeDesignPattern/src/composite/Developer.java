package composite;

public class Developer implements Employee {

    private String name;
    private String department;

    public Developer(String name, String department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public void showEmployeeDetails() {
        System.out.println("Developer: " + name);
        System.out.println("Department: " + department);
    }
}