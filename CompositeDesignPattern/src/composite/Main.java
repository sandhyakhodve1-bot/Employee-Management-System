package composite;

public class Main {

    public static void main(String[] args) {

        Developer developer1 =
                new Developer("Rahul", "Development");

        Developer developer2 =
                new Developer("Amit", "Development");

        Designer designer1 =
                new Designer("Priya", "Design");

        Manager manager =
                new Manager("Sandhya", "IT Manager");

        manager.addEmployee(developer1);
        manager.addEmployee(developer2);
        manager.addEmployee(designer1);

        manager.showEmployeeDetails();
    }
}