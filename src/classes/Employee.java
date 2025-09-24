package classes;

/**
 * Enum class for employees.
 * Represents the eligible employees who can book vehicles
 *
 * @author Jai Patel, Aaman Gafur
 */

public enum Employee {
    /** Ex: Employee named Patel who works in the Computer Science Department*/
    PATEL(Department.CS),
    LIM(Department.EE),
    ZIMNES(Department.CS),
    HARPER(Department.EE),
    KAUR(Department.ITI),
    TAYLOR(Department.MATH),
    RAMESH(Department.MATH),
    CERAVOLO(Department.BAIT);

    /** The department each employee belongs to */
    private final Department dept;

    /**
     * Constructor for Employee enum
     * Connects employees to their department
     *
     * @param dept The department for the Employee enum
     */
    Employee(Department dept) {
        this.dept = dept;
    }

    /**
     * Helper function to return department of employee
     *
     * @return The Department enum associated with Employee enum
     */
    public Department getDepartment(){
        return this.dept;
    }

    public static void main(String[] args){

        // Test Case 1: Printing out entire enum w/ its attribute
        for(Employee emp : Employee.values()) {
            System.out.println("Name: " + emp.name() + " Dept: " + emp.getDepartment());
        }

        System.out.println();

        // Test Case 2:
        Employee emp = Employee.PATEL;
        System.out.println("Employee works for " + emp.getDepartment());

        if(emp.getDepartment() == Department.CS){
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }
    }
}
