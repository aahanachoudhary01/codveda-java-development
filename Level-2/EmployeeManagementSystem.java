
import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void display() {
        System.out.println("ID: " + id
                + " | Name: " + name
                + " | Department: " + department
                + " | Salary: " + salary);
    }
}

public class EmployeeManagementSystem {

    private static final ArrayList<Employee> employees = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    private static Employee findEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    private static void addEmployee() {
        int id = readInt("Enter employee ID: ");

        if (findEmployee(id) != null) {
            System.out.println("Employee ID already exists!");
            return;
        }

        System.out.print("Enter employee name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter department: ");
        String department = sc.nextLine().trim();

        double salary = readDouble("Enter salary: ");

        if (name.isEmpty() || department.isEmpty() || salary < 0) {
            System.out.println("Invalid details. Please try again.");
            return;
        }

        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully!");
    }

    private static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        }

        System.out.println("\n--- Employee Records ---");
        for (Employee employee : employees) {
            employee.display();
        }
    }

    
private static void updateEmployee() {
    int id = readInt("Enter employee ID to update: ");
    Employee employee = findEmployee(id);

    if (employee == null) {
        System.out.println("Employee not found!");
        return;
    }

    System.out.println("\nWhat do you want to update?");
    System.out.println("1. Name");
    System.out.println("2. Department");
    System.out.println("3. Salary");
    System.out.println("4. Cancel");

    int choice = readInt("Enter your choice: ");

    switch (choice) {
        case 1:
            System.out.print("Enter new name: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                return;
            }

            employee.setName(name);
            System.out.println("Name updated successfully!");
            break;

        case 2:
            System.out.print("Enter new department: ");
            String department = sc.nextLine().trim();

            if (department.isEmpty()) {
                System.out.println("Department cannot be empty!");
                return;
            }

            employee.setDepartment(department);
            System.out.println("Department updated successfully!");
            break;

        case 3:
            double salary = readDouble("Enter new salary: ");

            if (salary < 0) {
                System.out.println("Salary cannot be negative!");
                return;
            }

            employee.setSalary(salary);
            System.out.println("Salary updated successfully!");
            break;

        case 4:
            System.out.println("Update cancelled.");
            break;

        default:
            System.out.println("Invalid choice!");
    }
}


    private static void deleteEmployee() {
        int id = readInt("Enter employee ID to delete: ");
        Employee employee = findEmployee(id);

        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        employees.remove(employee);
        System.out.println("Employee deleted successfully!");
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(value)) {
                    return value;
                }

                System.out.println("Please enter a finite number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    System.out.println("Exiting Employee Management System.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Select 1 to 5.");
            }
        }
    }
}

