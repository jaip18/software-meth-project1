package classes;

/**
 * This class implements the Trip object
 * Includes a Booking object, mileage of vehicle at the start of the trip, and
 * mileage at the end of trip.
 * Trip objects are equal if they have the same booking details.
 * @author Jai Patel
 */

public class Trip {
    private Booking booking;
    private int beginMileage;
    private int endMileage;

    /**
     * Empty constructor to create an empty Booking object.
     */
    public Trip(){
        this.booking = new Booking();
        this.beginMileage = 0;
        this.endMileage = 0;
    }

    /**
     * Copy constructor to create a copy of a Trip object.
     * @param other the Trip to copy
     */
    public Trip(Trip other){
        this.booking = other.booking;
        this.beginMileage = other.beginMileage;
        this.endMileage = other.endMileage;
    }

    /**
     * Constructs a Trip object with the given 3 parameters.
     *
     * @param booking the booking associated with the trip
     * @param beginMileage the starting value on the odometer of the vehicle used for the trip
     * @param endMileage the ending value on the odometer of the vehicle used for the trip
     */
    public Trip(Booking booking, int beginMileage, int endMileage){
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
    }

    /**
     * Getter for booking of the trip.
     *
     * @return the booking object associated with trip
     */
    public Booking getBooking(){
        return this.booking;
    }

    /**
     * Getter for the mileage of the vehicle at the start of the trip.
     *
     * @return the beginning mileage
     */
    public int getBeginMileage(){
        return this.beginMileage;
    }

    /**
     * Getter for the mileage of the vehicle at the rnd of the trip.
     *
     * @return the ending mileage
     */
    public int getEndMileage(){
        return this.endMileage;
    }

    /**
     * Two trips are equal if they have the same booking attributes.
     *
     * @param o the object to compare with
     * @return true if obj is a Trip with the same Booking object,
     *         false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Trip trip){
            return this.booking.equals(trip.booking);
        }
        return false;
    }

    /**
     * Returns a string representation of this Trip.
     * Format: {plate} {beginDate} ~ {endDate} original mileage:{beginMileage} current mileage:{endMileage}
                mileage used: {endMileage - beginMileage}
     *
     * @return formatted string of this Trip according to the instructions.
     */
    @Override
    public String toString(){
        int mileageUsed = this.endMileage - this.beginMileage;
        Booking booking = this.booking;
        return String.format("%s %s ~ %s original mileage: %s current mileage: %s mileage used: %s",
                booking.getVehicle().getPlate(), booking.getBegin(), booking.getEnd(),this.beginMileage,
                this.endMileage, mileageUsed);
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

        Trip trip1 = new Trip(b1, 100, 150);
        Trip trip2 = new Trip(b2, 250, 250);
        Trip trip3 = new Trip(b2, 210, 200);

        System.out.println(trip1);
        System.out.println(trip2);
        System.out.println(trip3);

        System.out.println(trip1.equals(trip2));
        System.out.println(trip1.equals(trip1));
    }
}
