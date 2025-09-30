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

                    break;
                case 'R': // return vehicle command

                    break;
                case 'P': // print command
                    String pCommand = request.substring(0,2);
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


}
