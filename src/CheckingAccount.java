public class CheckingAccount extends Account implements Transferable {
    private double overdraftLimit;

    public CheckingAccount(double balance, double overdraftLimit) {
        super(balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean transfer(Account targetAccount, double amount) {
        if (targetAccount != null && this.withdraw(amount)) {
            targetAccount.deposit(amount);
            return true;
        }
        return false;
    }

    @Override
    public String getAccountType() {
        return "Checking Account (Overdraft Limit: $" + overdraftLimit + ")";
    }
}