import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Reservation {
    private String passengerName;
    private String busNumber;

    public Reservation(String passengerName, String busNumber) {
        this.passengerName = passengerName;
        this.busNumber = busNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    @Override
    public String toString() {
        return "Passenger Name: " + passengerName + ", Bus Number: " + busNumber;
    }
}

class BusReservationSystem {
    private ArrayList<Reservation> reservations;

    public BusReservationSystem() {
        reservations = new ArrayList<>();
    }

    public void makeReservation(String passengerName, String busNumber) {
        Reservation reservation = new Reservation(passengerName, busNumber);
        reservations.add(reservation);
        System.out.println("Reservation successful for " + passengerName + " on bus " + busNumber);
    }

    public void showReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Current Reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
}

public class BusReservationApp { // Renamed class to avoid conflict
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BusReservationSystem busReservationSystem = new BusReservationSystem();

        boolean exit = false;

        while (!exit) {
            System.out.println("\nBus Reservation System");
            System.out.println("1. Make a Reservation");
            System.out.println("2. Show Reservations");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int option = -1;
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 3.");
                scanner.nextLine(); // Clear the invalid input
                continue; // Restart the loop
            }

            switch (option) {
                case 1:
                    System.out.print("Enter passenger name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter bus number: ");
                    String busNumber = scanner.nextLine();
                    busReservationSystem.makeReservation(name, busNumber);
                    break;
                case 2:
                    busReservationSystem.showReservations();
                    break;
                case 3:
                    System.out.println("Thank you for using the Bus Reservation System. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }

        scanner.close();
    }
}
