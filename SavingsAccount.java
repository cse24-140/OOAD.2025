public class SavingsAccount {
    public class Saving extends Account implements Deposit {
        private static final double INTEREST_RATE = 0.0025;
    
        public Saving(String accountNumber, double balance, String customer) {
            super(accountNumber, balance, customer);
        }
    
        @Override
        public void deposit(double amount) {
            balance += amount;
        }
    
        public void applyInterest() {
            balance += balance * INTEREST_RATE;
        }
    }
    
}
