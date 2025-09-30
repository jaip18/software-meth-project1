package classes;

/**
 * The Fleet class manages a resizable array of Vehicle objects.
 * Supports adding, removing, searching, and printing vehicles.
 * The array grows by 4 when it reaches full capacity (starts at 4).
 * @author Aaman Gafur, Jai Patel
 */
public class Fleet {
    private static final int CAPACITY = 4; //initial capacity
    private static final int NOT_FOUND = -1;
    private Vehicle[] fleet;
    private int size; //current number of vehicles in the fleet

    /**
     * Default constructor initializes the fleet with initial capacity.
     */
    public Fleet() {
        this.fleet = new Vehicle[CAPACITY];
        this.size = 0;
    }

    /**
     * Finds a vehicle in the fleet.
     * @param vehicle the vehicle to search for
     * @return index of vehicle if found, -1 otherwise
     */
    private int find(Vehicle vehicle) {
        for (int i = 0; i < size; i++) {
            if (this.fleet[i].equals(vehicle)) {
                return i;
            }
        }
        return NOT_FOUND;
    } //search the given vehicle

    /**
     * Grows the fleet capacity by 4.
     */
    private void grow() {
        Vehicle[] newFleet = new Vehicle[CAPACITY+fleet.length];
        for (int i = 0; i < size; i++) {
            newFleet[i] = fleet[i];
        }
        fleet = newFleet;
    } //resize the array

    /**
     * Adds a vehicle to the fleet.
     * If the fleet is full, grows by 4 using grow function.
     *
     * @param vehicle the vehicle that is being added
     */
    public void add(Vehicle vehicle) {
       if (this.searchByPlate(vehicle.getPlate()) != null){
           return;
       }
       if (size == fleet.length) {
           grow();
       }
       fleet[size] = vehicle;
       size=size+1;
    } //add to end of array

    /**
     * Removes a vehicle from the fleet by overwriting it with the last element.
     *
     * @param vehicle the vehicle that is being removed
     */
    public void remove(Vehicle vehicle) {
        int index = find(vehicle);
        if (index != NOT_FOUND){
            fleet[index] = fleet[size-1];
            size=size-1;
        }
    } //overwrite with last element

    /**
     * Checks whether the fleet contains the vehicle.
     * @param vehicle the vehicle that needs to be checked
     * @return true if vehicle exists, false otherwise
     */
    public boolean contains(Vehicle vehicle) {
        int index = find(vehicle);
        return index != NOT_FOUND;
    }

    /**
     * Checks whether the fleet contains the vehicle, according to its plate.
     * @param plate the vehicle.plate that needs to be checked
     * @return index of vehicle if it is found exists, -1 otherwise
     */
    public Vehicle searchByPlate(String plate) {
        for(int i = 0; i < this.size; i++){
            Vehicle vehicle = this.fleet[i];

            if (vehicle != null && vehicle.getPlate().equalsIgnoreCase(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Prints the fleet ordered by make and date obtained.
     * Uses in-place insertion sort. Prints a message if empty.
     */
    public void printByMake() {
        if (size == 0) {
            System.out.println("The fleet is empty (No cars in fleet).");
            return;
        }

        for (int i = 1; i < size; i++) {
            Vehicle key = fleet[i];
            int j = i - 1;

            while (j >= 0) {
                int compareMake = key.getMake().compareTo(fleet[j].getMake());
                boolean shouldShift = false;

                if (compareMake < 0) {
                    shouldShift = true;
                } else if (compareMake == 0 &&
                        key.getObtained().compareTo(fleet[j].getObtained()) < 0) {
                    shouldShift = true;
                }

                if (shouldShift) {
                    fleet[j + 1] = fleet[j];
                    j--;
                } else {
                    break;
                }
            }
            fleet[j + 1] = key;
        }

        for (int i = 0; i < size; i++) {
            System.out.println(fleet[i]);
        }
    } //ordered by make, then date obtained


    public static void main(String[] args) {
        Fleet fleet = new Fleet();

        Vehicle v1 = new Vehicle("123ABC", new Date(3, 10, 2023), Make.FORD, 50000);
        Vehicle v2 = new Vehicle("456XYZ", new Date(5, 15, 2021), Make.TOYOTA, 30000);
        Vehicle v3 = new Vehicle("789LMN", new Date(2, 20, 2022), Make.FORD, 25000);
        Vehicle v4 = new Vehicle("111AAA", new Date(1, 1, 2020), Make.HONDA, 60000);

        fleet.add(v1);
        fleet.add(v2);
        fleet.add(v3);
        fleet.add(v4);

        System.out.println("Fleet contains v2? " + fleet.contains(v2)); // expect true
        System.out.println("Fleet contains a random vehicle? " +
                fleet.contains(new Vehicle("999ZZZ", new Date(1, 1, 2020), Make.FORD, 1000))); // expect false

        System.out.println("\nPrinting fleet sorted by make/date:");
        fleet.printByMake();

        System.out.println("\nRemoving v2");
        fleet.remove(v2);
        fleet.printByMake();
    }


}
