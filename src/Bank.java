import java.util.ArrayList;

public class Bank {
    private final ArrayList<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<>();
    }

    public void addCustomer(String firstName, String lastName) {
        customers.add(new Customer(firstName, lastName));
    }

    public int getNumOfCustomers() {
        return customers.size();
    }

    public Customer getCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            return customers.get(index);
        }
        return null;
    }
}