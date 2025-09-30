package classes;
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
            System.out.print("> ");
            String request = scanner.nextLine().trim();
            if(request.isEmpty()){
                continue;
            }
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
                    isActive = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }

        System.out.println("Vehicle Management System is terminated.");
        scanner.close();
    }

    public void handleAddCommand(String request){
        String[] vehicleInfo = request.split(" ");

        try{
            if(vehicleInfo.length != 5){
                System.out.println("Invalid input format for ADD command. Format follows" +
                        "A plate MM/DD/YYYY MAKE mileage");
                return;
            }
            String plate = vehicleInfo[1];
            String dateString = vehicleInfo[2];
            String[] dateInfo = dateString.split("/");
            Date date = new Date(Integer.parseInt(dateInfo[0]),
                    Integer.parseInt(dateInfo[0]), Integer.parseInt(dateInfo[1]));
            Make make = Make.valueOf(vehicleInfo[3].toUpperCase());
            int mileage = Integer.parseInt(vehicleInfo[4]);

            fleet.add(new Vehicle(plate, date, make, mileage));
            System.out.println("Vehicle added to Fleet successfully.");
        }
        catch (NumberFormatException e){
            System.out.println("ERROR: Invalid number format for date or mileage.");
        }
        catch (IllegalArgumentException e){
            System.out.println("ERROR: Invalid vehicle make.");
        }
    }

    public void handleDeleteCommand(String request){
        String[] deleteParts = request.split(" ");

        if(deleteParts.length != 2){
            System.out.println("Invalid input format for DELETE command. Format follows" +
                    "D plate");
            return;
        }

        String deletePlate = deleteParts[1];
        Vehicle vehicle = fleet.searchByPlate(deletePlate);
        boolean isInFleet = (vehicle != null);

        if(!isInFleet){
            System.out.println("Vehicle is not in Fleet.");
            return;
        }

        boolean hasBookings = reservation.isBooked(deletePlate);

        if(hasBookings){
            System.out.println("Vehicle is already booked, cannot be deleted.");
            return;
        }

        fleet.remove(vehicle);
        System.out.println("Vehicle has been deleted from Fleet successfully.");
    }

    public void handleBookingCommand(String request){
        String[] bookingParts = request.split(" ");

        try{
            if(bookingParts.length != 5){
                System.out.println("Invalid input format for BOOKING command. Format follows" +
                        "B start_date end_date plate employee ");
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

            if(!beginDate.isValid() || !endDate.isValid()){
                System.out.println("Invalid begin or end date of booking");
                return;
            }

            if(bookedVehicle == null){
                System.out.println("Invalid booking, vehicle does not exist in fleet.");
                return;
            }

            //The vehicle associated with the license plate number is not available for the dates entered.
            if(reservation.isAvailable(bookingPlate, beginDate, endDate)){
                System.out.println("Invalid booking, vehicle is not available during that interval.");
                return;
            }

            //The employee has an existing booking conflicting with the dates entered.
            if(!reservation.hasTimeConflict(bookedBy, beginDate, endDate)){
                System.out.println("Invalid booking, employee entered has a time conflict.");
                return;
            }

            Booking addedBooking = new Booking(beginDate, endDate, bookedBy, bookedVehicle);

            if(addedBooking.isTooFarInAdvance() || addedBooking.isTooLong()){
                System.out.println("Invalid booking dates, either booking is over 7 days long " +
                        "or vehicle is booked on a date beyond 3 months");
                return;
            }

            reservation.add(addedBooking);
            System.out.println("Booking successfully added to Reservations.");
        }
        catch(NumberFormatException e){
            System.out.println("ERROR: Invalid number format for date.");
        }
        catch (IllegalArgumentException e){
            System.out.println("ERROR: Invalid employee name.");
        }
    }

    private void handleCancel(String line) {
        try {
            String[] parts = line.split("\\s+");
            if (parts.length != 4) {
                System.out.println("Invalid input format for CANCEL command. Format: C begin_date end_date plate");
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
            Booking dummyBooking = new Booking(begin, end, Employee.KAUR, dummyVehicle);

            if (reservation.contains(dummyBooking)) {
                reservation.remove(dummyBooking);
                System.out.println("Booking successfully cancelled.");
            } else {
                System.out.println("Booking cancellation failed: no matching booking found.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Invalid date or booking format.");
        }
    }


    private void handleReturn(String line) {
        try {
            String[] parts = line.split("\\s+");
            if (parts.length != 4) {
                System.out.println("Invalid input format for RETURN command. Format: R end_date plate mileage");
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
                System.out.println("Return failed: end date does not match the earliest booking end date.");
                return;
            }

            // Validate mileage
            if (newMileage <= vehicle.getMileage()) {
                System.out.println("Return failed: mileage cannot be less than or equal to current mileage.");
                return;
            }

            // Create Trip and add to tripList
            Trip trip = new Trip(booking, vehicle.getMileage(), newMileage);
            triplist.add(trip);

            // Update mileage in fleet
            vehicle.setMileage(newMileage);

            // Remove booking
            reservation.remove(booking);

            System.out.println("Vehicle returned successfully. Trip recorded.");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid number format for mileage or date.");
        } catch (Exception e) {
            System.out.println("ERROR: Invalid return command format.");
        }
    }


    private void handlePrint(String line) {
        switch (line) {
            case "PF" -> fleet.printByMake();
            case "PR" -> reservation.printByVehicle();
            case "PD" -> reservation.printByDept();
            case "PT" -> triplist.print();
            default -> System.out.println("Invalid print command.");
        }
    }
}
