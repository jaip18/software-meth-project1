package classes;

/**
 * This class implements the Booking object.
 * Includes a beginning date, end date, employee who booked, and vehicle,
 * Bookings are equal if they have the same beginning and end date, and same vehicle,
 * @author Jai Patel, Aaman Gafur
 */

public class Booking {
    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;

    /**
     * Copy constructor to create a copy of a Booking object.
     * @param other the Booking to copy
     */
    public Booking(Booking other){
        this.begin = new Date(other.begin);
        this.end = new Date(other.end);
        this.employee = other.employee;
        this.vehicle = new Vehicle(other.vehicle);
    }

    /**
     * Constructs a Booking object with the given 4 parameters.
     *
     * @param begin the beginning date of the booking
     * @param end the ending date of the booking
     * @param employee the employee making the booking
     * @param vehicle the vehicle being reserved
     */
    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    /**
     * Getter for the beginning date of the booking.
     *
     * @return the beginning date
     */
    public Date getBegin(){
        return this.begin;
    }

    /**
     * Getter for the ending date of the booking.
     *
     * @return the ending date
     */
    public Date getEnd(){
        return this.end;
    }

    /**
     * Getter for the employee who made the booking.
     *
     * @return the employee
     */
    public Employee getEmployee(){
        return this.employee;
    }

    /**
     * Getter for the vehicle reserved in the booking.
     *
     * @return the vehicle
     */
    public Vehicle getVehicle(){
        return this.vehicle;
    }

    /**
     * Two bookings are equal if they have the same vehicle, beginning date, and ending date.
     *
     * @param o the object to compare with
     * @return true if obj is a Booking with the same vehicle,
     *         beginning date, and ending date, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof Booking){
            Booking booking = (Booking) o;
            return booking.begin.equals(this.begin)
                    && booking.end.equals(this.end)
                    && booking.vehicle.equals(this.vehicle);
        }
        return false;
    }

    /**
     * Returns a string representation of this Booking.
     * Format: plate:make:obtained [mileage:value] [beginning M/D/YYYY ending M/D/YYYY:EMPLOYEE]
     *
     * @return formatted string of this Booking according to the instructions.
     */
    @Override
    public String toString(){
        return String.format("%s:%s:%s [mileage:%s] [beginning %s ending %s:%s]",
                this.vehicle.getPlate(), this.vehicle.getMake(),
                this.vehicle.getObtained(), this.vehicle.getMileage(),
                this.begin, this.end, this.employee);
    }

    public static void main(String[] args) {
        Date obtained = new Date(11, 1, 2019);
        Vehicle v1 = new Vehicle("67359S", obtained, Make.FORD, 59644);
        Vehicle v2 = new Vehicle("123ABC", obtained, Make.FORD, 60000);

        Date begin1 = new Date(10, 31, 2025);
        Date end1   = new Date(11, 2, 2025);
        Date begin2 = new Date(10, 31, 2025);
        Date end2   = new Date(11, 2, 2025);

        Booking b1 = new Booking(begin1, end1, Employee.KAUR, v1);
        Booking b2 = new Booking(begin2, end2, Employee.KAUR, new Vehicle(v1)); // same as b1
        Booking b3 = new Booking(begin1, end1, Employee.KAUR, v2);              // different vehicle

        System.out.println("toString() tests:");
        System.out.println("Booking 1: " + b1);
        System.out.println("Booking 2: " + b2);
        System.out.println("Booking 3: " + b3);
        System.out.println();

        System.out.println("equals() tests:");
        System.out.println("b1.equals(b2) (expect true): " + b1.equals(b2));
        System.out.println("b1.equals(b3) (expect false): " + b1.equals(b3));
    }


}










