import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringApplication;
import java.io.*;
import java.util.*;


public class VRMS {

    static ArrayList<String> vehicleId = new ArrayList<>();
    static ArrayList<String> vehicleCompany = new ArrayList<>();
    static ArrayList<String> vehicleModel = new ArrayList<>();
    static ArrayList<String> vehicleType = new ArrayList<>();
    static ArrayList<Boolean> vehicleRented = new ArrayList<>();
    static ArrayList<Integer> rentalCount = new ArrayList<>();
    static String fileName = "vehicles.txt";

    // Method to save vehicle data to a file
    static void saveVehicleData() {
        try (FileOutputStream fos = new FileOutputStream(fileName); 
                PrintStream ps = new PrintStream(fos)) {
            for (int i = 0; i < vehicleId.size(); i++) {
                ps.println(vehicleId.get(i) + "," + vehicleCompany.get(i) + "," + vehicleModel.get(i) + "," +
                        vehicleType.get(i) + "," + vehicleRented.get(i) + "," + rentalCount.get(i));
            }
            System.out.println("Vehicle data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving vehicle data: " + e);
        }
    }

    // Method to load vehicle data from a file
    static void loadVehicleData() {
        try (FileInputStream fis = new FileInputStream(fileName); 
                Scanner input = new Scanner(fis)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split(",");
                vehicleId.add(data[0]);
                vehicleCompany.add(data[1]);
                vehicleModel.add(data[2]);
                vehicleType.add(data[3]);
                boolean isRented = Boolean.parseBoolean(data[4]);
                int count = Integer.parseInt(data[5]);

                vehicleRented.add(isRented);
                rentalCount.add(count);
                
            }
            System.out.println("Vehicle data loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading vehicle data: " + e);
        }
    }

    // Method to clear all vehicle data
    static void clearVehicleData() {
        vehicleId.clear();
        vehicleCompany.clear();
        vehicleModel.clear();
        vehicleType.clear();
        vehicleRented.clear();
        rentalCount.clear();
        System.out.println("All vehicle data cleared successfully.");

    }

    // Method to add a vehicle
    static void addVehicle(Scanner input) {
        System.out.print("Enter Vehicle ID: ");
        String id = input.next();
        if (vehicleId.contains(id)) {
        System.out.println("Error: Vehicle ID already exists. Please choose a different ID.");
        return; // Exit the method to prevent adding duplicate ID
    }
        System.out.print("Enter Company Name: ");
        String company = input.next();
        System.out.print("Enter Model: ");
        String model = input.next();
        System.out.print("Enter Type (Car/Truck/Bike): ");
        String type = input.next();
        
        
        vehicleId.add(id);
        vehicleCompany.add(company);
        vehicleModel.add(model);
        vehicleType.add(type);
        vehicleRented.add(false);
        rentalCount.add(0);

        System.out.println("Vehicle added successfully.");
    }

    // Method to rent a vehicle
    static void rentVehicle(Scanner input) {
        System.out.print("Enter Vehicle ID to rent: ");
        String id = input.next();
        for (int i = 0; i < vehicleId.size(); i++) {
            if (vehicleId.get(i).equals(id) && !vehicleRented.get(i)) {
                vehicleRented.set(i, true);
                rentalCount.set(i, rentalCount.get(i) + 1);
                System.out.println("Vehicle rented successfully.");
                return;
            }
        }
        System.out.println("Vehicle not found or already rented.");
    }

    // Method to return a vehicle
    static void returnVehicle(Scanner input) {
        System.out.print("Enter Vehicle ID to return: ");
        String id = input.next();
        for (int i = 0; i < vehicleId.size(); i++) {
            if (vehicleId.get(i).equals(id) && vehicleRented.get(i)) {
                vehicleRented.set(i, false);
                System.out.println("Vehicle returned successfully.");
                return;
            }
        }
        System.out.println("Vehicle not found or not rented.");
    }

    // Method to list all vehicles
    static void listVehicles() {
        if (vehicleId.isEmpty()) {
            System.out.println("No vehicles added yet.");
            return;
        }
        System.out.println("List of Vehicles:");
        for (int i = 0; i < vehicleId.size(); i++) {
            System.out.println("Data of vehicle "+ (i+1) +"....."+"\n ID: " + vehicleId.get(i) + "\n Company: "
                    + vehicleCompany.get(i) + "\n Model: "
                    + vehicleModel.get(i) + "\n Type: " + vehicleType.get(i) + "\n Rented: "
                    + (vehicleRented.get(i) ? "Yes" : "No") + "\n Times Rented: " + rentalCount.get(i));
        }
    }

    // Method to search for vehicles by make or model
    static void searchVehicles(Scanner input) {
        System.out.print("Enter Company Name or Model to search: ");
        String searchQuery = input.next();
        
        for (int i = 0; i < vehicleId.size(); i++) {
            if (vehicleCompany.get(i).equalsIgnoreCase(searchQuery)
                    || vehicleModel.get(i).equalsIgnoreCase(searchQuery)) {
                System.out.println("ID: " + vehicleId.get(i) + ", Make: " + vehicleCompany.get(i) + ", Model: "
                        + vehicleModel.get(i) + ", Type: " + vehicleType.get(i) + ", Rented: "
                        + (vehicleRented.get(i) ? "Yes" : "No"));
                return;
            }
        }       
        System.out.println("No vehicles found matching the search criteria.");      
    }

    // Main Method
    public static void main(String[] args) {


        loadVehicleData(); // Load vehicle data from file at the start

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nVehicle Rental System");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Rent Vehicle");
            System.out.println("3. Return Vehicle");
            System.out.println("4. List Vehicles");
            System.out.println("5. Search Vehicles");
            System.out.println("6. Clear All Vehicle Data");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    addVehicle(input);
                    break;
                case 2:
                    rentVehicle(input);
                    break;
                case 3:
                    returnVehicle(input);
                    break;
                case 4:
                    listVehicles();
                    break;
                case 5:
                    searchVehicles(input);
                    break;
                case 6:
                    clearVehicleData();
                    break;
                case 7:
                    saveVehicleData(); // Save vehicle data to file before exiting
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }
}
