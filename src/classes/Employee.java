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
}
