import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        bank.addCustomer("John", "Doe");
        // Menggunakan Polymorphism: Menambahkan SavingsAccount sebagai Account
        bank.getCustomer(0).addAccount(new SavingsAccount(1000.0, 0.05));

        while (running) {
            System.out.println("\n==================================");
            System.out.println("     SIMPLE BANKING ATM MENU      ");
            System.out.println("==================================");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Account to Customer");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. View Customer Info & Balance");
            System.out.println("6. Exit");
            int choice = readInt(scanner, "Choose an option (1-6): ");

            switch (choice) {
                case 1: {
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine().trim();
                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine().trim();
                    bank.addCustomer(firstName, lastName);
                    System.out.println("Customer added successfully!");
                    break;
                }

                case 2: {
                    if (bank.getNumOfCustomers() == 0) {
                        System.out.println("No customers available.");
                        break;
                    }
                    displayCustomers(bank);
                    int customerIndex = readInt(scanner, "Select Customer Index: ");
                    Customer customer = bank.getCustomer(customerIndex);

                    if (customer != null) {
                        System.out.println("Select Account Type:");
                        System.out.println("1. Savings Account");
                        System.out.println("2. Checking Account");
                        int type = readInt(scanner, "Choose type (1-2): ");

                        double initialBalance = readAmount(scanner, "Enter Initial Balance: ", true);

                        if (type == 1) {
                            customer.addAccount(new SavingsAccount(initialBalance, 0.03));
                            System.out.println("Savings Account added successfully!");
                        } else if (type == 2) {
                            customer.addAccount(new CheckingAccount(initialBalance, 500.0));
                            System.out.println("Checking Account added successfully!");
                        } else {
                            System.out.println("Invalid Account Type.");
                        }
                    } else {
                        System.out.println("Invalid Customer Index.");
                    }
                    break;
                }

                case 3:
                    performTransaction(bank, scanner, true);
                    break;

                case 4:
                    performTransaction(bank, scanner, false);
                    break;

                case 5: {
                    if (bank.getNumOfCustomers() == 0) {
                        System.out.println("No customers registered.");
                        break;
                    }
                    System.out.println("\n--- ALL CUSTOMERS & ACCOUNTS ---");
                    for (int i = 0; i < bank.getNumOfCustomers(); i++) {
                        Customer c = bank.getCustomer(i);
                        System.out.println("Customer [" + i + "]: " + c.getFirstName() + " " + c.getLastName());
                        for (int j = 0; j < c.getNumOfAccounts(); j++) {
                            Account acc = c.getAccount(j);
                            // Memanggil method getAccountType() secara Polymorphic
                            System.out.println("  -> Account [" + j + "] (" + acc.getAccountType() + ") Balance: $" + acc.getBalance());
                        }
                    }
                    break;
                }

                case 6:
                    running = false;
                    System.out.println("Thank you for using the Banking Application!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    private static void performTransaction(Bank bank, Scanner scanner, boolean isDeposit) {
        Customer customer = selectCustomer(bank, scanner);
        if (customer == null) return;
        if (customer.getNumOfAccounts() == 0) {
            System.out.println("This customer has no accounts.");
            return;
        }

        for (int i = 0; i < customer.getNumOfAccounts(); i++) {
            Account acc = customer.getAccount(i);
            System.out.println("[" + i + "] " + acc.getAccountType() + " | Balance: $" + acc.getBalance());
        }
        int accountIndex = readInt(scanner, "Select Account Index: ");
        Account account = customer.getAccount(accountIndex);
        if (account == null) {
            System.out.println("Invalid Account Index.");
            return;
        }

        String prompt = isDeposit ? "Enter Deposit Amount: " : "Enter Withdrawal Amount: ";
        double amount = readAmount(scanner, prompt, false);
        boolean successful = isDeposit ? account.deposit(amount) : account.withdraw(amount);
        if (!successful) {
            System.out.println("Transaction failed: Insufficient balance or invalid amount.");
        } else if (isDeposit) {
            System.out.println("Deposit successful! New Balance: $" + account.getBalance());
        } else {
            System.out.println("Withdrawal successful! Remaining Balance: $" + account.getBalance());
        }
    }

    private static void displayCustomers(Bank bank) {
        System.out.println("\nCustomer List:");
        for (int i = 0; i < bank.getNumOfCustomers(); i++) {
            Customer c = bank.getCustomer(i);
            System.out.println("[" + i + "] " + c.getFirstName() + " " + c.getLastName());
        }
    }

    private static Customer selectCustomer(Bank bank, Scanner scanner) {
        if (bank.getNumOfCustomers() == 0) {
            System.out.println("No customers available.");
            return null;
        }
        displayCustomers(bank);
        int customerIndex = readInt(scanner, "Select Customer Index: ");
        Customer customer = bank.getCustomer(customerIndex);
        if (customer == null) {
            System.out.println("Invalid Customer Index.");
        }
        return customer;
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readAmount(Scanner scanner, String prompt, boolean allowZero) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                boolean invalid = Double.isNaN(amount) || Double.isInfinite(amount)
                        || amount < 0 || (!allowZero && amount == 0);
                if (!invalid) {
                    return amount;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println(allowZero
                    ? "Please enter a finite, non-negative amount."
                    : "Please enter a finite amount greater than zero.");
        }
    }
}