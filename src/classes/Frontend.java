package classes;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * This class serves as the user interface for the Vehicle Management System.
 * The UI handles all command-line inputs and directs input requests to the
 * intended container classes.
 * @author Jai Patel, Aaman Gafur
 */

public class Frontend {
    private Fleet fleet;
    private Reservation reservation;
    private TripList triplist;

    public Frontend(){
        this.fleet = new Fleet();
        this.reservation = new Reservation();
        this.triplist = new TripList();
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        System.out.println("Vehicle Management System is live.");
        while(isActive){
            String request = scanner.nextLine().trim();
            if(request.isEmpty()) continue;
            char command = request.toUpperCase().charAt(0);
            switch(command) {
                case 'A': // add vehicle command
                    handleAddCommand(request);
                    break;
                case 'D': // delete vehicle command
                    handleDeleteCommand(request);
                    break;
                case 'B': // book vehicle command
                    handleBookingCommand(request);
                    break;
                case 'C': // cancel booking command
                    handleCancel(request);
                    break;
                case 'R': // return vehicle command
                    handleReturn(request);
                    break;
                case 'P': // print command
                    String pCommand = request.substring(0,2);
                    handlePrint(pCommand);
                    break;
                case 'Q': // quit command
                    isActive = handleQuit(request);
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private void handleAddCommand(String request){
        System.out.print("> ");
        String[] vehicleInfo = request.split(" ");

        try{
            // check if request is properly formatted for add command
            if(vehicleInfo.length != 5){
                System.out.println("a - invalid command!");
                return;
            }
            String plate = vehicleInfo[1];
            String dateString = vehicleInfo[2];
            String[] dateInfo = dateString.split("/");
            Date date = new Date(Integer.parseInt(dateInfo[0]),
                    Integer.parseInt(dateInfo[1]), Integer.parseInt(dateInfo[2]));
            Make make = Make.valueOf(vehicleInfo[3].toUpperCase());
            int mileage = Integer.parseInt(vehicleInfo[4]);

            // check if vehicle is in fleet already
//            if(fleet.contains(fleet.searchByPlate(plate))){
//                System.out.println("Vehicle is already in Fleet. Cannot be added. ");
//                return;
//            }

            // checks if mileage is valid
            if(mileage <= 0){
                System.out.println(mileage + " - invalid mileage.");
                return;
            }

            // checks if date is valid and not in the future
            if(!date.isValid()){
                System.out.println(date + " - invalid calendar date.");
                return;
            }
            if(date.isTodayOrFuture()){
                System.out.println(date + " - is today or future date.");
                return;
            }

            Vehicle addedVehicle = new Vehicle(plate, date, make, mileage);
            fleet.add(addedVehicle);
            System.out.println(addedVehicle + " has been added to the fleet.");
        }
        catch (NumberFormatException e){
            System.out.println("ERROR: Invalid number format for date or mileage.");
        }
        catch (IllegalArgumentException e){
            System.out.println(vehicleInfo[3] + " - invalid make.");
        }
    }

    private void handleDeleteCommand(String request){
        System.out.print("> ");
        String[] deleteParts = request.split(" ");

        // check if request is correctly formatted for delete commands
        if(deleteParts.length != 2){
            System.out.println("d - invalid command!");
            return;
        }

        String deletePlate = deleteParts[1];
        Vehicle vehicle = fleet.searchByPlate(deletePlate);
        boolean isInFleet = (vehicle != null);

        // check if vehicle is in fleet, so it can be deleted
        if(!isInFleet){
            System.out.println(deletePlate + " is not in the fleet.");
            return;
        }

        boolean hasBookings = reservation.isBooked(deletePlate);

        // check if vehicle has bookings before deleting
        if(hasBookings){
            System.out.println(deletePlate + " - has existing bookings; cannot be resolved");
            return;
        }

        fleet.remove(vehicle);
        System.out.println(vehicle + " has been removed from the fleet.");
    }

    private void handleBookingCommand(String request){
        System.out.print("> ");
        String[] bookingParts = request.split("\\s+");

        try{

            // check if request is properly formatted for booking command
            if(bookingParts.length != 5){
                System.out.println("b - invalid command!");
                return;
            }

            String beginString = bookingParts[1];
            String[] beginInfo = beginString.split("/");
            Date beginDate = new Date(Integer.parseInt(beginInfo[0]), Integer.parseInt(beginInfo[1]),
                    Integer.parseInt(beginInfo[2]));

            String endString = bookingParts[2];
            String[] endInfo = endString.split("/");
            Date endDate = new Date(Integer.parseInt(endInfo[0]), Integer.parseInt(endInfo[1]),
                    Integer.parseInt(endInfo[2]));

            String bookingPlate = bookingParts[3];
            Vehicle bookedVehicle = fleet.searchByPlate(bookingPlate);

            Employee bookedBy = Employee.valueOf(bookingParts[4].toUpperCase());

            // checks if both dates are valid
            if(!beginDate.isValid()){
                System.out.println(beginDate + " - beginning date is not a valid calendar date.");
                return;
            }

            if(!endDate.isValid()){
                System.out.println(endDate + " - ending date is not a valid calendar date.");
                return;
            }

            if(!beginDate.isTodayOrFuture()){
                System.out.println(beginDate + " beginning date is not today or a future date.");
                return;
            }

            // checks if the end date is before the start date
            if(endDate.compareTo(beginDate) < 0){
                System.out.println(endDate + " - ending date must be equal or after the beginning date "
                                + beginDate);
                return;
            }

            // checks if vehicle being requested exists
            if(bookedVehicle == null){
                System.out.println(bookingPlate + " is not in the fleet.");
                return;
            }

            // checks on availability of vehicle
            if(!reservation.isAvailable(bookingPlate, beginDate, endDate)){
                System.out.println(bookingPlate + " - booking with " + beginDate + " ~ " + endDate + " not available.");
                return;
            }

            // checks if employee is available for the request booking
            if(reservation.hasTimeConflict(bookedBy, beginDate, endDate)){
                System.out.println(bookingParts[4].toUpperCase() + " - has an existing booking conflicting with beginning date " + beginDate);
                return;
            }

            Booking addedBooking = new Booking(beginDate, endDate, bookedBy, bookedVehicle);

            // checks if booking is either too far in advance
            if(addedBooking.isTooFarInAdvance()){
                System.out.println(beginDate + " - beginning date is beyond 3 months.");
                return;
            }

            // checks if booking is too long
            if(addedBooking.isTooLong()){
                System.out.println(beginDate + " ~ " + endDate + " - duration more than a week.");
                return;
            }

            reservation.add(addedBooking);
            System.out.println(addedBooking + " booked.");
        }
        catch(NumberFormatException e){
            System.out.println("ERROR: Invalid number format for date.");
        }
        catch (IllegalArgumentException e){
            System.out.println(bookingParts[4] + " not an eligible employee to book.");
        }
    }

    private void handleCancel(String line) {
        System.out.print("> ");
        try {
            String[] parts = line.split("\\s+");
            if (parts.length != 4) {
                System.out.println("c - invalid command!");
                return;
            }

            // Parse begin date
            String[] beginInfo = parts[1].split("/");
            Date begin = new Date(
                    Integer.parseInt(beginInfo[0]),
                    Integer.parseInt(beginInfo[1]),
                    Integer.parseInt(beginInfo[2])
            );

            // Parse end date
            String[] endInfo = parts[2].split("/");
            Date end = new Date(
                    Integer.parseInt(endInfo[0]),
                    Integer.parseInt(endInfo[1]),
                    Integer.parseInt(endInfo[2])
            );

            String plate = parts[3];

            // Build a "dummy booking" for lookup
            Vehicle dummyVehicle = fleet.searchByPlate(plate);
            if (dummyVehicle == null) {
                System.out.println("Booking cancellation failed: vehicle not found in fleet.");
                return;
            }

            // Dummy employee since equals() only cares about vehicle, begin, and end
            Booking dummyBooking = new Booking(begin, end, null, dummyVehicle);

            if (reservation.contains(dummyBooking)) {
                reservation.remove(dummyBooking);
                System.out.println(plate + ":" + begin + " ~ " + end + " has been canceled.");
            } else {
                System.out.println(plate + ":" + begin + " ~ " + end + " - cannot find the booking.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Invalid date or booking format.");
        }
    }

    private void handleReturn(String line) {
        System.out.print("> ");
        try {
            String[] parts = line.split("\\s+");
            if (parts.length != 4) {
                System.out.println("r - invalid command!");
                return;
            }

            // Parse end date
            String[] endInfo = parts[1].split("/");
            Date endDate = new Date(
                    Integer.parseInt(endInfo[0]),
                    Integer.parseInt(endInfo[1]),
                    Integer.parseInt(endInfo[2])
            );

            String plate = parts[2];
            int newMileage = Integer.parseInt(parts[3]);

            // Find vehicle
            Vehicle vehicle = fleet.searchByPlate(plate);
            if (vehicle == null) {
                System.out.println("Return failed: vehicle not found in fleet.");
                return;
            }

            // Find earliest booking for that vehicle
            Booking booking = reservation.getEarliestBooking(plate);
            if (booking == null) {
                System.out.println("Return failed: no active booking for this vehicle.");
                return;
            }

            // Check that the end date matches the earliest booking
            if (!endDate.equals(booking.getEnd())) {
                System.out.println(plate + " booked with ending date " + endDate + " - cannot find the booking.");
                return;
            }

            if(newMileage <= 0){
                System.out.println(newMileage + " - invalid mileage.");
                return;
            }

            // Validate mileage
            if (newMileage <= vehicle.getMileage()) {
                System.out.println("Invalid mileage - current mileage: " + vehicle.getMileage() + " entered mileage: " + newMileage);
                return;
            }

            // Create Trip and add to tripList
            Trip trip = new Trip(booking, vehicle.getMileage(), newMileage);
            triplist.add(trip);

            int originalMileage = vehicle.getMileage();
            int mileageUsed = newMileage - originalMileage;

            // Update mileage in fleet
            vehicle.setMileage(newMileage);

            // Remove booking
            reservation.remove(booking);

            System.out.println("Trip completed: " + plate + " " + booking.getBegin() + " ~ "+ endDate + " original mileage: " +
                    originalMileage + " current mileage: " + newMileage + " " +
                    "mileage used: " + mileageUsed);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid number format for mileage or date.");
        } catch (Exception e) {
            System.out.println("ERROR: Invalid return command format.");
        }
    }

    private void handlePrint(String line) {
        System.out.print("> ");
        switch (line) {
            case "PF" -> fleet.printByMake();
            case "PR" -> reservation.printByVehicle();
            case "PD" -> reservation.printByDept();
            case "PT" -> triplist.print();
            default -> System.out.println(line + " - invalid command!");
        }
    }

    private boolean handleQuit(String line){
        System.out.print("> ");
        if (line.equals("q")){
            System.out.println("q - invalid command!");
            return true;
        }

        System.out.println("Vehicle Management System is terminated.");
        return false;
    }

}
