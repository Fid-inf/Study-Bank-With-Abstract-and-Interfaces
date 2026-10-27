public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(double balance, double interestRate) {
        super(balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        deposit(interest);
    }

    @Override
    public String getAccountType() {
        return "Savings Account (Interest Rate: " + (interestRate * 100) + "%)";
    }
}