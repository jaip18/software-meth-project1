package classes;
/**
 * This class implements the Vehicle object
 @author Aaman Gafur, Jai Patel
 */
public class Vehicle implements Comparable<Vehicle> {
    private String plate; //license plate number
    private Date obtained; //Date class described in the next page
    private Make make; //Make is an enum class
    private int mileage; //current reading on the odometer

    /**
     * Default constructor for a date object
     */
    public Vehicle() {
        this.plate = null;
        this.obtained = new Date();
        this.make = null;
        this.mileage = 0;
    }

    /**
     * Parameterized Constructor with 4 parameters.
     * Overloaded constructor for Vehicle.
     *
     * @param plate plate number of the vehicle
     * @param obtained the day the vehicle was obtained
     * @param make the make/brand of the vehicle
     * @param mileage mileage of the car when obtained
     */
    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;
    }

    /**
     * Copy constructor to clone a Vehicle
     * @param other the Vehicle containing the data to be copied.
     */
    public Vehicle(Vehicle other) {
        this.plate = other.plate;
        this.obtained = new Date(other.obtained);
        this.make = other.make;
        this.mileage = other.mileage;
    }

    /**
     * Getter for plate number.
     * @return the plate number of this vehicle.
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Getter for date obtained.
     * @return the date the vehicle has been obtained.
     */
    public Date getObtained() {
        return obtained;
    }

    /**
     * Getter for make.
     * @return the make of this vehicle.
     */
    public Make getMake() {
        return make;
    }

    /**
     * Getter for mileage.
     * @return the mileage of this vehicle.
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Updates the mileage of the vehicle.
     * @param mileage the new mileage value to set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * Checks whether this vehicle is equal to another object.
     * Two vehicles are considered equal if they have the same license plate.
     * @param o the object being compared to this vehicle
     * @return true if o is a Vehicle with the same license plate, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) o;
            return vehicle.plate.equals(this.plate);
        }
        return false;
    }

    /**
    * a.compareTo(b); return -1, 0, 1
    * @param other the other vehicle that is being compared to this vehicle.
    * @return a negative integer if a<b, 0 if equal, positive integer if a>b
    *
    * */
    @Override
    public int compareTo(Vehicle other) {  // assume a.compareTo(b) is called
        int makeCompare = this.make.compareTo(other.make); // uses enum's compareTo
        if (makeCompare > 0) {
            return 1;
        }
        if (makeCompare < 0) {
            return -1;
        }
        // same make → compare by obtained date
        return this.obtained.compareTo(other.obtained);
    }

    /**
     * Return a textual representation of the Vehicle object.
     * @return a string containing the vehicle's plate,
     * make, date obtained, and mileage
     */
    @Override
    public String toString() {
        return plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]";
    }

    public static void main(String[] args) {
        // Create some Date objects
        Date d1 = new Date(2, 29, 2020);
        Date d2 = new Date(5, 15, 2021);
        Date d3 = new Date(2, 29, 2020); // same as d1

        // Create some Vehicle objects
        Vehicle v1 = new Vehicle("123ABC", d1, Make.FORD, 50000);
        Vehicle v2 = new Vehicle("456XYZ", d2, Make.TOYOTA, 30000);
        Vehicle v3 = new Vehicle("123ABC", d3, Make.FORD, 60000); // same plate as v1
        Vehicle v4 = new Vehicle("789LMN", d2, Make.FORD, 20000);

        System.out.println("compareTo() output:");
        System.out.println(v1.compareTo(v2)); // compare FORD vs TOYOTA (depends on enum order)
        System.out.println(v1.compareTo(v3)); // same make, same date -> 0
        System.out.println(v2.compareTo(v1)); // same make, earlier date -> -1 or 1 depending on order
    }


}
