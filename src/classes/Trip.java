package classes;

/**
 * This class implements the Trip object
 * @author Jai Patel
 */

public class Trip {
    private Booking booking;
    private int beginMileage;
    private int endMileage;

    public Trip(){
        this.booking = new Booking();
        this.beginMileage = 0;
        this.endMileage = 0;
    }

    public Trip(Trip other){
        this.booking = other.booking;
        this.beginMileage = other.beginMileage;
        this.endMileage = other.endMileage;
    }

    public Trip(Booking booking, int beginMileage, int endMileage){
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
    }

    public Booking getBooking(){
        return this.booking;
    }

    public int getBeginMileage(){
        return this.beginMileage;
    }

    public int getEndMileage(){
        return this.endMileage;
    }

    public void setBooking(Booking booking){
        this.booking = booking;
    }

    public void setBeginMileage(int beginMileage){
        this.beginMileage = beginMileage;
    }

    public void setEndMileage(int endMileage){
        this.endMileage = endMileage;
    }

    public boolean isValid(){
        return this.booking.isValid() && this.getEndMileage() - this.beginMileage >= 0;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Trip trip){
            return this.booking.equals(trip.booking);
        }
        return false;
    }

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

        System.out.println(trip3.isValid());
        System.out.println(trip2.isValid());

        System.out.println(trip1.equals(trip2));
        System.out.println(trip1.equals(trip1));



    }
}
