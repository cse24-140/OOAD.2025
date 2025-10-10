public class Main {
    public static void main(String[] args) {
        // Create an account
        Account acc = new Account("AC001", 1500.00, "C001");
        System.out.println("Account Created:");
        System.out.println(acc.toString());
        System.out.println("-------------------------");

        // Create a deposit transaction
        Transaction deposit = new Transaction("T001", acc.getAccountNumber(), 500.00, "Deposit");
        System.out.println(" New Transaction:");
        System.out.println(deposit.toString());
        System.out.println("-------------------------");

        // Update account balance after deposit
        acc.balance += deposit.getAmount();
        System.out.println(" Updated Account Balance: " + acc.getBalance());

        // Create a withdrawal transaction
        Transaction withdrawal = new Transaction("T002", acc.getAccountNumber(), 200.00, "Withdrawal");
        System.out.println("\n New Transaction:");
        System.out.println(withdrawal.toString());
        System.out.println("-------------------------");

        // Update account balance after withdrawal
        acc.balance -= withdrawal.getAmount();
        System.out.println("Updated Account Balance: " + acc.getBalance());
    }
}
