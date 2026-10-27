public abstract class Account {
    protected double balance;

    public Account(double balance) {
        if (Double.isNaN(balance) || Double.isInfinite(balance) || balance < 0) {
            throw new IllegalArgumentException("Initial balance must be a finite, non-negative amount.");
        }
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount > 0 && !Double.isNaN(amount) && !Double.isInfinite(amount)
            && !Double.isInfinite(balance + amount)) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && !Double.isNaN(amount) && !Double.isInfinite(amount) && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // ABSTRACT METHOD: Wajib di-override oleh subclass (SavingsAccount / CheckingAccount)
    public abstract String getAccountType();
}