#include <stdio.h>
#include <string.h>

// Struct for each customer
typedef struct {
    char name[10];
    int AU;
    int US;
    int EU;
} Customer;

// Function prototypes
void readFile(Customer customers[], int *size);
void outputCSV(Customer customers[], int size);
void printCustomers(Customer customers[], int *size);

int main() {
    //Array of customer structs
    Customer customers[10];//Struct array can only hold 10 elements

    //variable that will store the size of the array
    int size = 0;

    // Read data from file
    readFile(customers, &size);

    //printCustomers(customers, &size); //function to check that the struct array is correct

    int option;
    char name[10];

    while (1) {
        printf("1. Enter name\n");
        printf("2. Exit\n");
        printf("Enter option: ");
        scanf("%d", &option);

        if (option == 1) {
            printf("Enter name: ");
            scanf("%s", name);

            int found = 0;
            for (int i = 0; i < size; i++) {
                if (strcmp(customers[i].name, name) == 0){
                    printf("\nCustomer:\n");
                    printf("%s %d cents in AU$\n", customers[i].name, customers[i].AU);
                    printf("Change:\n");
                    if (customers[i].AU >= 50) {
                        printf("50 cents: %d\n", customers[i].AU / 50);
                    }
                    if (customers[i].AU >= 20) {
                        printf("20 cents: %d\n", (customers[i].AU % 50) / 20);
                    }
                    if (customers[i].AU >= 10) {
                        printf("10 cents: %d\n", (customers[i].AU % 20) / 10);
                    }
                    if (customers[i].AU >= 5) {
                        printf("5 cents: %d\n", (customers[i].AU % 10) / 5);
                    }
                    printf("\n");
                    found = 1;
                    break;
                }
            }

            if (!found) {
                printf("\nName: %s\nNot found\n\n", name);
            }
        } else if (option == 2) {
            // Write data to file
            outputCSV(customers, size);
            break;
        } else {
            printf("Invalid option. Please try again.\n\n");
        }
    }

    return 0;
}

void readFile(Customer customers[], int *size) {
    FILE *file = fopen("coins.txt", "r");
    if (file == NULL) {
        printf("Error opening file.\n");
        return;
    }

    char name[10];
    int cents;
    char currency[4];

    while (fscanf(file, "%s %d cents in %s", name, &cents, currency) != EOF) {
        // Check if the customer already exists in the array
        int found = 0;
        for (int i = 0; i < *size; i++) {
            if (strcmp(customers[i].name, name) == 0) {  // customer found
                if (strcmp(currency, "AU$") == 0) {
                    customers[i].AU += cents;
                } else if (strcmp(currency, "US$") == 0) {
                    customers[i].US += cents;
                } else if (strcmp(currency, "EUR") == 0) {
                    customers[i].EU += cents;
                }
                found = 1;
                break;
            }
        }

        // If the customer was not found, add a new one
        if (!found && *size < 10) {
            strcpy(customers[*size].name, name);
            //Initialize all variables in a new struct as zero to avoid having uninitialized values
            //*****If this step is not done, there will be uninitialized values where the customer does
            //not have any currency, and will show up as random garbage values when access******
            customers[*size].AU = 0;
            customers[*size].US = 0;
            customers[*size].EU = 0;
            if (strcmp(currency, "AU$") == 0) {
                customers[*size].AU = cents;
            } else if (strcmp(currency, "US$") == 0) {
                customers[*size].US = cents;
            } else if (strcmp(currency, "EUR") == 0) {
                customers[*size].EU = cents;
            }
            (*size)++;
        }
    }

    fclose(file);
}

void printCustomers(Customer customers[], int *size) {
    printf("This is printing the customer array\n");
    for (int i = 0; i < *size; i++){
        printf ("%s:\nAU: %d\nUS: %d\nEU: %d\n\n", customers[i].name, customers[i].AU, customers[i].US, customers[i].EU);
    }

}

void outputCSV(Customer customers[], int size) {
    FILE *file = fopen("change.csv", "w");
    if (file == NULL) {
        printf("Error opening file.\n");
        return;
    }

    for (int i = 0; i < size; i++) {
        // Calculate AU change
        if (customers[i].AU > 0) {
            int coin50 = customers[i].AU / 50;
            int remainder = customers[i].AU % 50;
            int coin20 = remainder / 20;
            remainder %= 20;
            int coin10 = remainder / 10;
            remainder %= 10;
            int coin5 = remainder / 5;

            fprintf(file, "%s, the change for %d cents in AU$ is %d x 50 cents, %d x 20 cents, %d x 10 cents, %d x 5 cents\n",
                    customers[i].name, customers[i].AU, coin50, coin20, coin10, coin5);
        }

        // Calculate US change
        if (customers[i].US > 0) {
            int coin50 = customers[i].US / 50;
            int remainder = customers[i].US % 50;
            int coin25 = remainder / 25;
            remainder %= 25;
            int coin10 = remainder / 10;
            remainder %= 10;
            int coin1 = remainder;

            fprintf(file, "%s, the change for %d cents in US$ is %d x 50 cents, %d x 25 cents, %d x 10 cents, %d x 1 cent\n",
                    customers[i].name, customers[i].US, coin50, coin25, coin10, coin1);
        }

        // Calculate EU change
        if (customers[i].EU > 0) {
            int coin20 = customers[i].EU / 20;
            int remainder = customers[i].EU % 20;
            int coin10 = remainder / 10;
            remainder %= 10;
            int coin5 = remainder / 5;
            int coin1 = remainder % 5;

            fprintf(file, "%s, the change for %d cents in EU$ is %d x 20 cents, %d x 10 cents, %d x 5 cents, %d x 1 cent\n",
                    customers[i].name, customers[i].EU, coin20, coin10, coin5, coin1);
        }
    }

    fclose(file);
}
