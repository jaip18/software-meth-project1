package classes;

/**
 * This class implements the TripList object
 * Includes a node of the last completed trip added to the list, and the size of the list
 * Trips are printed out in a list order by their ending dates.
 *
 * @author Jai Patel
 */

public class TripList {
    /**
     * Private inner class representing a single node in the circular linked list.
     * Includes a trip and a pointer to the next trip in the list
     */
    private static class Node{
        private final Trip trip;
        private Node next;

        /**
         * Constructor for the Node object using 1 parameter.
         *
         * @param trip The Trip object data
         */
        public Node(Trip trip){
            this.trip = trip;
            this.next = null;
        }
    }

    private Node last;

    /**
     * Default constructor to create an empty TripList object.
     */
    public TripList(){
        this.last = null;
    }

    /**
     * Getter for the last trip in the list.
     *
     * @return last trip in list
     */
    public Node getLast(){
        return this.last;
    }

    /**
     * Getter for the size of the trip list.
     *
     * @return the size of list
     */
    public int getSize(){
        if(last == null){
            return 0;
        }

        Node curr = this.last.next;
        int size = 0;

        do{
            curr = curr.next;
            size++;
        } while (curr != this.last.next);

        return size;
    }

    /**
     * Adds a completed Trip object to the end of the circular linked list.
     *
     * @param trip The Trip object to be added
     */
    public void add(Trip trip){
        Node newNode = new Node(trip);

        if(this.getSize() == 0){
            newNode.next = newNode;
        }
        else {
            newNode.next = this.last.next;
            this.last.next = newNode;
        }

        this.last = newNode;
    }

    /**
     * Prints out the data of the Nodes within the TripList object.
     * Trips will be printed out based on order of their ending dates
     */
    public void print(){
        if(this.getSize() == 0){
            System.out.println("No trips have been completed yet.");
            return;
        }

        Trip[] array = this.toArray();
        insertionSort(array);

        System.out.println("*** Completed trips (Sorted by End Date) ***");
        for (Trip trip : array) {
            System.out.println(trip);
        }
    }

    /**
     * Helper method to convert the linked list of Trip objects, into an array.
     *
     * @return an Array of Trip objects
     */
    public Trip[] toArray(){
        if(this.getSize() == 0){
            return new Trip[0];
        }

        Trip[] tripArray = new Trip[this.getSize()];

        Node curr = this.last.next;

        for(int i = 0; i < this.getSize(); i++){
            tripArray[i] = curr.trip;
            curr = curr.next;
        }

        return tripArray;
    }

    /**
     * Helper method to sort the given array of Trip objects using the Insertion Sort algorithm.
     * The sort order is based on the comparison of each trip's end date.
     *
     * @param array The Trip array to be sorted in place
     */
    private void insertionSort(Trip[] array) {
        int n = array.length;

        for(int i = 1; i < n; i++){
            Trip keyTrip = array[i];
            Date keyDate = keyTrip.getBooking().getEnd();

            int j = i - 1;

            while(j >= 0){
                Date compareDate = array[j].getBooking().getEnd();
                if(keyDate.compareTo(compareDate) < 0){
                    array[j + 1] = array[j];
                    j = j - 1;
                }
                else {
                    break;
                }
            }

            array[j + 1] = keyTrip;
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

        Booking b1 = new Booking(d1, d5, Employee.KAUR, new Vehicle());
        Booking b2 = new Booking(d1, d3, Employee.KAUR, new Vehicle());
        Booking b3 = new Booking(d1, d6, Employee.KAUR, new Vehicle());
        Booking b4 = new Booking(d1, d7, Employee.KAUR, new Vehicle());
        Booking b5 = new Booking(d1, d8, Employee.KAUR, new Vehicle());

        Trip t1 = new Trip(b5, 10, 20);
        Trip t2 = new Trip(b1, 10, 20);
        Trip t3 = new Trip(b2, 10, 20);
        Trip t4 = new Trip(b3, 10, 20);
        Trip t5 = new Trip(b4, 10, 20);

        TripList list = new TripList();
        list.add(t1);
        list.print();
        list.add(t2);
        list.print();
        list.add(t3);
        list.print();
        list.add(t4);
        list.print();
        list.add(t5);
    }

}

