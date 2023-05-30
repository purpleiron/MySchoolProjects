import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CoinReader {
    /**
     * Read the customer data from a txt file and return a list of customers.
     *
     * @param filename The name of the file to read.
     * @return The list of customers.
     */
    public ArrayList<Customer> readCoinsFromFile(String filename) {
        //initialize an arraylist that will store Customer objects
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            // Open the file for reading
            File file = new File(filename);
            //Scanner object will read the file
            Scanner scanner = new Scanner(file);

            // Read each line of the file
            while (scanner.hasNextLine()) {
                //take the line and put it into the line variable
                //use.trim() to take out any spaces in the front or back
                String line = scanner.nextLine().trim();


                //check if there is another line
                if (!line.isEmpty()) {
                    // Split the line into an array of parts based on spaces
                    String[] parts = line.split("\\s+", 4);

                    if (parts.length == 4) {
                        // Extract the name, amount, and currency from the line
                        String name = parts[0];
                        int amount = extractAmount(parts[1]);
                        String currency = parts[3];

                        // Find or create the corresponding customer
                        Customer customer = findCustomerByName(customers, name);
                        if (customer == null) {
                            // Create a new customer and add it to the list
                            customer = new Customer(name);
                            customers.add(customer);
                        }

                        // Add the amount to the customer's currency
                        customer.addAmount(currency, amount);
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        return customers;
    }

    /**
     * Extract the amount from the given amount string.
     *
     * @param amountString The amount string.
     * @return The extracted amount as an integer.
     */
    private int extractAmount(String amountString) {
        // Remove non-digit characters from the amount string
        String amount = amountString.replaceAll("[^0-9]", "");

        // Parse the amount as an integer
        return Integer.parseInt(amount);
    }

    /**
     * Find a customer in the list by name.
     *
     * @param customers The list of customers.
     * @param name      The name of the customer to find.
     * @return The found customer, or null if not found.
     */
    private Customer findCustomerByName(ArrayList<Customer> customers, String name) {
        // Iterate over the list of customers
        for (Customer customer : customers) {
            // Perform a case-insensitive check on the customer name
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer; // Return the found customer
            }
        }
        return null; // Customer not found
    }
}
