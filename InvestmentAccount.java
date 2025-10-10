public class InvestmentAccount {
    public class Investment extends Account implements Deposit {
        private static final double INTEREST_RATE = 0.05;
        private static final double MIN_DEPOSIT = 500;
    
        public Investment(String accountNumber, double balance, String customer) {
            super(accountNumber, balance, customer);
        }
    
        @Override
        public void deposit(double amount) {
            if (amount >= MIN_DEPOSIT) {
                balance += amount;
            } else {
                System.out.println("Minimum deposit is BWP500");
            }
        }
    
        public void applyInterest() {
            balance += balance * INTEREST_RATE;
        }
    }
    
}
