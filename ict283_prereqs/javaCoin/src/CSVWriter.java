import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {
    /**
     * Write the customer data to a CSV file.
     *
     * @param filename  The name of the CSV file.
     * @param customers The list of customers.
     */
    public void writeToCSV(String filename, ArrayList<Customer> customers) {
        try {
            FileWriter writer = new FileWriter(filename);

            // Write the header line
            writer.append("Name,Currency,Change\n");

            // Write the customer data
            for (Customer customer : customers) {
                writeCustomerData(writer, customer);
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + filename);
        }
    }

    /**
     * Write the data for a customer to the CSV file.
     *
     * @param writer   The FileWriter object.
     * @param customer The customer to write.
     * @throws IOException If an I/O error occurs.
     */
    private void writeCustomerData(FileWriter writer, Customer customer) throws IOException {
        String name = customer.getName();

        // Write the AU$ data
        int auAmount = customer.getAuAmount();
        if (auAmount > 0) {
            writer.append(name).append(",AU$,").append(getChangeString(auAmount)).append("\n");
        }

        // Write the EUR data
        int euroAmount = customer.getEuroAmount();
        if (euroAmount > 0) {
            writer.append(name).append(",EUR,").append(getChangeString(euroAmount)).append("\n");
        }

        // Write the US$ data
        int usAmount = customer.getUsAmount();
        if (usAmount > 0) {
            writer.append(name).append(",US$,").append(getChangeString(usAmount)).append("\n");
        }
    }

    /**
     * Get the change string for the given amount.
     *
     * @param amount The amount.
     * @return The change string.
     */
    private String getChangeString(int amount) {
        // Calculate the number of coins for each denomination
        int fiftyCents = amount / 50;
        int twentyCents = (amount % 50) / 20;
        int tenCents = (amount % 20) / 10;
        int fiveCents = (amount % 10) / 5;
        int oneCent = amount % 5;

        // Build the change string
        return fiftyCents + "," + twentyCents + "," + tenCents + "," + fiveCents + "," + oneCent;
    }
}
