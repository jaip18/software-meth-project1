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
     * In this example, we assume if two events are equal if the plate
     * number (string) is the same
     * @param o the reference to the vehicle to be compared
     * @return return true if two event vehicles are equal; return false otherwise.
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
    * @param vehicle
    * @return a negative integer if a<b, 0 if equal, positive integer if a>b
    *
    * */
    @Override
    public int compareTo(Vehicle vehicle) {
        if (this.plate.compareTo(vehicle.plate)<0){
            return -1;
        }
        if (this.plate.compareTo(vehicle.plate)>0){
            return 1;
        }
        return 0;
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




}
