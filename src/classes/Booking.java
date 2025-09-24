package classes;

/**
 * This class implements the Booking object
 * @author Jai Patel
 */

public class Booking {
    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;


    public Booking(){
        this.begin = new Date();
        this.end = new Date();
        this.employee = null;
        this.vehicle = new Vehicle();
    }

    public Booking(Booking other){
        this.begin = other.begin;
        this.end = other.end;
        this.employee = other.employee;
        this.vehicle = other.vehicle;
    }

    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    public Date getBegin(){
        return this.begin;
    }

    public Date getEnd(){
        return this.end;
    }

    public Employee getEmployee(){
        return this.employee;
    }

    public Vehicle getVehicle(){
        return this.vehicle;
    }

    public void setBegin(Date date){
        this.begin = date;
    }

    public void setEnd(Date date){
        this.end = date;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public boolean isValid(){
        if(!this.begin.isValid() || !this.end.isValid()){
            return false;
        }

        else if(this.begin.compareTo(this.end) > 0){
            return false;
        }

        else if(this.employee == null){
            return false;
        }

        return this.vehicle != null;
    }

    @Override
    // booking validity check needed
    public boolean equals(Object o){
        if (o instanceof Booking booking && this.isValid() && booking.isValid()){
            return this.vehicle.equals(booking.vehicle) && this.begin.equals(booking.begin) && this.end.equals(booking.end);
        }

        return false;
    }

    @Override
    public String toString(){
        return String.format("%s:%s:%s [mileage:%s] [beginning %s ending %s:%s]", this.vehicle.getPlate(), this.vehicle.getMileage(),
                this.vehicle.getObtained(), this.vehicle.getMileage(), this.begin, this.end, this.employee);
    }

    public static void main(String[] args){
        Date d1 = new Date(1, 20, 2025);
        Date d2 = new Date(10, 1, 2000);
        Date d3 = new Date(13, 32, 2025);

        Vehicle v1 = new Vehicle("123ABC", d1, Make.FORD, 50000);
        Vehicle v2 = new Vehicle("456XYZ", d2, Make.TOYOTA, 30000);
        Vehicle v3 = new Vehicle("123ABC", d3, Make.FORD, 60000); // same plate as v1

        Booking b1 = new Booking(d2, d1, Employee.KAUR, v3);
        Booking b2 = new Booking(d1, d3, Employee.KAUR, v1);
        Booking b3 = new Booking(d1, d2, Employee.KAUR, v2);
        Booking b4 = new Booking(d1, d3, Employee.KAUR, new Vehicle());


        // checking printing format
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(b4);
        System.out.println();

        // testing isValid method
        System.out.println(b1.isValid()); // true
        System.out.println(b2.isValid()); // false
        System.out.println(b3.isValid()); // false
        System.out.println(b4.isValid()); // false
        System.out.println();

        // testing compareTo method
        System.out.println(b1.equals(new Booking(d2, d1, Employee.KAUR, v3))); // true
        System.out.println(b2.equals(new Booking(d1, d3, Employee.KAUR, v1))); // true
        System.out.println(b2.equals(b4)); // false threw invalid booking
        System.out.println(b1.equals(b3)); // false
    }

}
