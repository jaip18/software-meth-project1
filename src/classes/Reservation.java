package classes;

/**
 * This class implements the Reservation object
 * Includes an array of bookings and the number of bookings total.
 *
 * @author Jai Patel
 */
public class Reservation {
    private Booking[] bookings;
    private int size;

    /**
     * Default constructor to create an empty Reservation object.
     */
    public Reservation(){
        this.bookings = new Booking[4];
        this.size = 0;
    }

    /**
     * Getter for the array of bookings.
     *
     * @return array of bookings
     */
    public Booking[] getBookings(){
        return this.bookings;
    }

    /**
     * Getter for the size of the bookings array.
     *
     * @return the size of the bookings array
     */
    public int getSize(){
        return this.size;
    }

    // Define the constant for not found
    private static final int NOT_FOUND = -1;

    /**
     * Searches through bookings array to check if passed through booking
     * is present in the array.
     *
     * @param book booking to be searched for
     * @return index where booking is located, otherwise NOT_FOUND is returned
     */
    private int find(Booking book){
        Booking[] bookings = this.bookings;

        for(int i = 0; i < bookings.length; i++){
            if(book.equals(bookings[i])){
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Resizes the amount of bookings the array can handle. The new resized array
     * will have a length of the previous array plus 4.
     */
    private void grow(){
        int capacity = this.bookings.length +4;

        Booking[] resizedList = new Booking[capacity];

        for(int i = 0; i < this.size; i++){
            resizedList[i] = this.bookings[i];
        }

        this.bookings = resizedList;
    }

    /**
     * This method will append a new booking to the of the list of bookings.
     * It will first check if the list is full, if so the array will grow, if not
     * the new booking will be added at the end of the list.
     */
    public void add(Booking booking){
        if(size == this.bookings.length){
            this.grow();
        }

        bookings[size] = booking;
        size++;
    }

    /**
     * This method will delete a booking, and accept a Booking object as a
     * parameter.
     * If Booking object is found in the list, it will be removed and all
     * subsequent elements will be shifted over.
     *
     * @param booking specific Booking to be removed from the list
     */
    public void remove(Booking booking){
        int index = this.find(booking);

        if (index == NOT_FOUND){
            return;
        }

        for(int i = index; i < this.size - 1; i++){
            this.bookings[i] = this.bookings[i + 1];
        }

        this.size--;

        this.bookings[this.size] = null;
    }

    /**
     * Searches through bookings array to check if passed through booking
     * is present in the array.
     *
     * @param booking booking to be searched for
     * @return true if not, false if not found
     */
    public boolean contains(Booking booking) {
        return this.find(booking) != NOT_FOUND;
    }

    /**
     * Searches through bookings array to check a vehicle has been booked
     *
     * @param plate booking to be searched for by plate
     * @return true if booked, false if not booked
     */
    public boolean isBooked(String plate){
        for (int i = 0; i < size; i++) {
            if (bookings[i].getVehicle().getPlate().equals(plate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches through bookings array to check if a vehicle with a given license
     * plate has a booking conflict. A vehicle is determined to be unavailable if
     * there is any overlap with an existing booking date.
     *
     * @param plate plate of vehicle whose availability is being checked
     * @param begin proposed date for start of booking
     * @param end proposed date for ending of booking
     * @return true if available, false if unavailable
     */
    public boolean isAvailable(String plate, Date begin, Date end){
        for(int i = 0; i < this.size; i++){
            Booking existingBooking = this.bookings[i];

            if(existingBooking.getVehicle().getPlate().equals(plate)){
                Date existingBegin = existingBooking.getBegin();
                Date existingEnd = existingBooking.getEnd();

                if (begin.compareTo(existingEnd) <= 0 && end.compareTo(existingBegin) >= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Searches through bookings array to check if a employee has a booking
     * conflict. A vehicle is determined to be unavailable if there is any
     * overlap with an existing booking date.
     *
     * @param employee employee to check for time conflicts
     * @param begin proposed date for start of booking
     * @param end proposed date for ending of booking
     * @return true if a conflicting booking is found, false if otherwise
     */
    public boolean hasTimeConflict(Employee employee, Date begin, Date end){
        for(int i = 0; i < this.size; i++) {
            Booking existingBooking = this.bookings[i];

            if (existingBooking.getEmployee().equals(employee)) {
                Date existingBegin = existingBooking.getBegin();
                Date existingEnd = existingBooking.getEnd();

                if (begin.compareTo(existingEnd) <= 0 && end.compareTo(existingBegin) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Prints out bookings, order based upon the plates of the vehicles,
     * then the date obtained
     *
     */
    public void printByVehicle(){
        if (this.size == 0) {
            System.out.println("There is no booking record.");
            return;
        }

        insertionSortByPlate(this.bookings);

        System.out.println("*List of reservations ordered by license plate number and beginning date.");
        for (int i = 0; i < size; i++) {
            System.out.println(bookings[i]);
        }
        System.out.println("*end of list.");
    }

    /**
     * Helper method to printByVehicle(), which sorts the booking array
     * based upon plates then the date obtained using Insertion Sort.
     *
     * @param array booking array to be sorted in place by plate
     */
    private void insertionSortByPlate(Booking[] array) {
        int n = this.size;

        for (int i = 1; i < n; i++) {
            Booking keyBooking = array[i];
            String keyPlate = keyBooking.getVehicle().getPlate();
            Date keyBegin = keyBooking.getBegin();

            int j = i - 1;

            while (j >= 0) {
                String comparePlate = array[j].getVehicle().getPlate();
                Date compareBegin = array[j].getBegin();

                // Sort by plate first, then by begin date if plates are equal
                if (keyPlate.compareTo(comparePlate) < 0 ||
                        (keyPlate.equals(comparePlate) && keyBegin.compareTo(compareBegin) < 0)) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }

            array[j + 1] = keyBooking;
        }
    }

    /**
     * Prints out bookings, order based upon the departments of the
     * employees who booked the trips, then by order by employee
     *
     */
    public void printByDept(){
        if(this.size == 0){
            System.out.println("There is no booking record.");
            return;
        }

        insertionSortByDept(bookings);
        String dept = "";

        System.out.println("*List of reservations ordered by department and employee.");
        for (int i = 0; i < size; i++) {

            if (!dept.equals(bookings[i].getEmployee().getDepartment().toString())) {
                dept = bookings[i].getEmployee().getDepartment().toString();
                System.out.println("--" + dept + "--");
            }
            System.out.println(bookings[i]);
        }
        System.out.println("end of list.");
    }

    /**
     * Finds the earliest booking for a specific vehicle in the reservations list.
     * A booking is considered earliest if its ending date is the smallest
     * compared to other bookings of the same vehicle.
     * @param plate the license plate of the vehicle whose earliest booking is being searched
     * @return the earliest Booking object for the vehicle, or null if no bookings are found
     */
    public Booking getEarliestBooking(String plate) {
        Booking earliest = null;
        for (int i = 0; i < size; i++) {
            Booking b = bookings[i];
            if (b.getVehicle().getPlate().equals(plate)) {
                if (earliest == null || b.getEnd().compareTo(earliest.getEnd()) < 0) {
                    earliest = b;
                }
            }
        }
        return earliest;
    }

    /**
     * Helper method to printByDept(), which sorts the booking array
     * based upon departments then the employees using Insertion Sort.
     *
     * @param array booking array to be sorted in place by department
     */
    private void insertionSortByDept(Booking[] array){
        int n = this.size;

        for (int i = 1; i < n; i++) {
            Booking keyBooking = array[i];
            Department keyDept = keyBooking.getEmployee().getDepartment();
            String keyEmployee = keyBooking.getEmployee().name();

            int j = i - 1;

            while (j >= 0) {
                Department compareDept = array[j].getEmployee().getDepartment();
                String compareEmployee = array[j].getEmployee().name();

                // Sort by department first, then by employee if departments are equal
                if (keyDept.compareTo(compareDept) < 0 ||
                        (keyDept == compareDept && keyEmployee.compareTo(compareEmployee) < 0)) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }

            array[j + 1] = keyBooking;
        }
    }

    public static void main(String[] args){
        Date d1 = new Date(1, 18, 2025);
        Date d2 = new Date(1, 19, 2025);
        Date d3 = new Date(3, 21, 2025);
        Date d4 = new Date(2, 23, 2025);
        Date d5 = new Date(3, 15, 2025);
        Date d6 = new Date(4, 15, 2025);
        Date d7 = new Date(5, 16, 2025);
        Date d8 = new Date(8, 23, 2025);

        Vehicle v1 = new Vehicle("123ABC", d1, Make.FORD, 50000);
        Vehicle v2 = new Vehicle("456XYZ", d2, Make.TOYOTA, 30000);
        Vehicle v3 = new Vehicle("123ABC", d3, Make.FORD, 60000); // same plate as v1
        Vehicle v4 = new Vehicle("789LMN", d2, Make.FORD, 20000);

        Booking b1 = new Booking(d1, d5, Employee.KAUR, v1);
        Booking b2 = new Booking(d1, d3, Employee.PATEL, v4);
        Booking b3 = new Booking(d1, d6, Employee.HARPER, v2);
        Booking b4 = new Booking(d1, d7, Employee.RAMESH, v3);
        Booking b5 = new Booking(d1, d8, Employee.TAYLOR, v2);

        Reservation reservation = new Reservation();
        reservation.add(b1);
        reservation.add(b2);
        reservation.add(b3);
        reservation.add(b4);
        reservation.add(b5);
        System.out.println("Reservation size:" + reservation.size);
        System.out.println();
        System.out.println("Printing by Dept");
        reservation.printByDept();
        System.out.println("Printing by Plate");
        reservation.printByVehicle();
    }
}
