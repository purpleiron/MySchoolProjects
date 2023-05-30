public class Customer {
    private String name;
    private int auAmount;
    private int euroAmount;
    private int usAmount;

    public Customer(String name) {
        this.name = name;
        this.auAmount = 0;
        this.euroAmount = 0;
        this.usAmount = 0;
    }

    /**
     * Get the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the total amount of coins in AU$.
     *
     * @return The total amount of coins in AU$.
     */
    public int getAuAmount() {
        return auAmount;
    }

    /**
     * Get the total amount of coins in EUR.
     *
     * @return The total amount of coins in EUR.
     */
    public int getEuroAmount() {
        return euroAmount;
    }

    /**
     * Get the total amount of coins in US$.
     *
     * @return The total amount of coins in US$.
     */
    public int getUsAmount() {
        return usAmount;
    }

    /**
     * Add the given amount of coins to the corresponding currency.
     *
     * @param currency The currency of the coins.
     * @param amount   The amount of coins to add.
     */
    public void addAmount(String currency, int amount) {
        // Remove the "in" string from the currency
        currency = currency.replace("in ", "");

        // Perform a case-insensitive check on the currency string
        if (currency.equalsIgnoreCase("AU$")) {
            // Add the amount to the AU$ currency
            auAmount += amount;
        } else if (currency.equalsIgnoreCase("EUR")) {
            // Add the amount to the EUR currency
            euroAmount += amount;
        } else if (currency.equalsIgnoreCase("US$")) {
            // Add the amount to the US$ currency
            usAmount += amount;
        }
    }
}
