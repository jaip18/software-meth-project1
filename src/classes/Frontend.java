package classes;
import java.util.Scanner;

/**
 * This class serves as the user interface for the Vehicle Management System.
 * The UI handles all command-line inputs and directs input requests to the
 * intended container classes.
 * @author Jai Patel, Aaman Gafur
 */

public class Frontend {
    public static void main(String[] args){
        Fleet fleet = new Fleet();
        Reservation reservation = new Reservation();
        TripList triplist = new TripList();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Vehicle Management System is live.");

        boolean isActive = true;

        while(isActive){
            System.out.print("> ");
            String request = scanner.nextLine().trim();

            if(request.isEmpty()){
                continue;
            }

            char command = request.toUpperCase().charAt(0);

            switch(command) {
                case 'A': // add vehicle command
                    String[] vehicleInfo = request.split(" ");

                    try{
                        if(vehicleInfo.length != 5){
                            System.out.println("Invalid input format for ADD command. Format follows" +
                                    "A plate MM/DD/YYYY MAKE mileage");
                            break;
                        }
                        String plate = vehicleInfo[1];
                        String dateString = vehicleInfo[2];
                        String[] dateInfo = dateString.split("/");
                        Date date = new Date(Integer.parseInt(dateInfo[0]),
                                Integer.parseInt(dateInfo[0]), Integer.parseInt(dateInfo[1]));
                        Make make = Make.valueOf(vehicleInfo[2].toUpperCase());
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
                    break;
                case 'D': // delete vehicle command
                    String[] deleteParts = request.split(" ");

                    if(deleteParts.length != 2){
                        System.out.println("Invalid input format for DELETE command. Format follows" +
                                "D plate");
                        break;
                    }

                    String plate = deleteParts[1];

                    boolean isInFleet = fleet.contains(new Vehicle(plate, new Date(), null, 0));


                    break;
                case 'B': // book vehicle command
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
}
