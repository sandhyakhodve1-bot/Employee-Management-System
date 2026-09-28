package composite;

public class Designer implements Employee {

    private String name;
    private String department;

    public Designer(String name, String department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public void showEmployeeDetails() {
        System.out.println("Designer: " + name);
        System.out.println("Department: " + department);
    }
}