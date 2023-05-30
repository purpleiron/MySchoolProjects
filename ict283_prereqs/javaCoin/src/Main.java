import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of CoinReader to read the customer data from the file
        CoinReader coinReader = new CoinReader();

        // Read the customer data from the file and store it in an ArrayList
        ArrayList<Customer> customers = coinReader.readCoinsFromFile("coins.txt");

        //printCustomerData(customers); //This is only for debugging purposes
        // Display the menu to the user
        displayMenu(customers);
    }

    private static void displayMenu(ArrayList<Customer> customers) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Enter name");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    // Find the customer by name in the ArrayList
                    Customer customer = findCustomerByName(customers, name);

                    if (customer != null) {
                        // Display the customer's data
                        displayCustomerData(customer);
                    } else {
                        System.out.println("Name: " + name);
                        System.out.println("Not found");
                    }
                    break;
                case 2:
                    // Create an instance of CSVWriter to write the customer data to a CSV file
                    CSVWriter csvWriter = new CSVWriter();

                    // Write the customer data to the CSV file
                    csvWriter.writeToCSV("change.csv", customers);

                    System.out.println("Data saved to change.csv. Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 2);

        scanner.close();
    }

    private static Customer findCustomerByName(ArrayList<Customer> customers, String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    private static void displayCustomerData(Customer customer) {
        System.out.println("\nCustomer:");
        System.out.println(customer.getName());
        System.out.println("AU$: " + customer.getAuAmount() + " cents");
        System.out.println("EUR: " + customer.getEuroAmount() + " cents");
        System.out.println("US$: " + customer.getUsAmount() + " cents");
    }

    private static void printCustomerData(ArrayList<Customer> customers) {
        System.out.println("\nCustomer Data:");
        for (Customer customer : customers) {
            System.out.println("\nCustomer:");
            System.out.println(customer.getName());
            System.out.println("AU$: " + customer.getAuAmount() + " cents");
            System.out.println("EUR: " + customer.getEuroAmount() + " cents");
            System.out.println("US$: " + customer.getUsAmount() + " cents");
        }
    }
}
