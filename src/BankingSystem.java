import java.util.*;

public class BankingSystem {
    private static BankingSystem instance;
    private Map<String, Customer> customers;
    private Map<String, Account> accounts;
    private int customerCounter;
    private int accountCounter;

    private BankingSystem() {
        customers = new HashMap<>();
        accounts = new HashMap<>();
        customerCounter = 1;
        accountCounter = 1;
        initializeSampleData();
    }

    public static BankingSystem getInstance() {
        if (instance == null) {
            instance = new BankingSystem();
        }
        return instance;
    }

    private void initializeSampleData() {
        // Create sample individual customer
        IndividualCustomer john = new IndividualCustomer(
                "IND00001",
                "John",
                "Smith",
                "19901567891",
                "71123456",
                "Gaborone",
                "john.smith@email.com",
                "password123"
        );

        // Create sample company customer
        CompanyCustomer abcCorp = new CompanyCustomer(
                "BUS00001",
                "ABC Corporation",
                "C123456",
                "Jane Doe",
                "71777666",
                "Francistown",
                "info@abccorp.com",
                "abc123"
        );

        customers.put(john.getCustomerId(), john);
        customers.put(abcCorp.getCustomerId(), abcCorp);

        // Create sample accounts
        SavingsAccount johnSavings = new SavingsAccount("ACC00001", 5000.00, john.getCustomerId());
        ChequeAccount johnCheque = new ChequeAccount("ACC00002", 2000.00, john.getCustomerId(), "ABC Company", "Gaborone");
        ChequeAccount abcCheque = new ChequeAccount("ACC00003", 15000.00, abcCorp.getCustomerId(), "Self", "Francistown");

        accounts.put(johnSavings.getAccountNumber(), johnSavings);
        accounts.put(johnCheque.getAccountNumber(), johnCheque);
        accounts.put(abcCheque.getAccountNumber(), abcCheque);

        john.addAccount(johnSavings);
        john.addAccount(johnCheque);
        abcCorp.addAccount(abcCheque);

        System.out.println("Sample data initialized with " + customers.size() + " customers and " + accounts.size() + " accounts");
    }

    // Customer Management
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("Customer added: " + customer.getCustomerId() + " (" + customer.getCustomerType() + ")");
    }

    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    public Map<String, Customer> getCustomers() {
        return new HashMap<>(customers);
    }

    public int getCustomerCount() {
        return customers.size();
    }

    // Account Management
    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        Customer customer = getCustomer(account.getCustomerId());
        if (customer != null) {
            customer.addAccount(account);
        }
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> getCustomerAccounts(String customerId) {
        Customer customer = getCustomer(customerId);
        return (customer != null) ? customer.getAccounts() : new ArrayList<>();
    }

    public double getCustomerTotalBalance(String customerId) {
        Customer customer = getCustomer(customerId);
        return (customer != null) ? customer.getTotalBalance() : 0.0;
    }

    // Transaction Methods
    public boolean deposit(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null && amount > 0) {
            account.deposit(amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null && account.withdraw(amount)) {
            return true;
        }
        return false;
    }

    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = getAccount(fromAccountNumber);
        Account toAccount = getAccount(toAccountNumber);

        if (fromAccount != null && toAccount != null && fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            return true;
        }
        return false;
    }

    // Account Creation
    public String createAccount(String customerId, String accountType, double initialDeposit) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return null;
        }

        String accountNumber = "ACC" + String.format("%05d", accountCounter++);
        Account newAccount;

        if ("Savings".equalsIgnoreCase(accountType)) {
            newAccount = new SavingsAccount(accountNumber, initialDeposit, customerId);
        } else if ("Cheque".equalsIgnoreCase(accountType)) {
            newAccount = new ChequeAccount(accountNumber, initialDeposit, customerId, "Default Employer", "Default Address");
        } else if ("Investment".equalsIgnoreCase(accountType)) {
            try {
                newAccount = new InvestmentAccount(accountNumber, initialDeposit, customerId);
            } catch (IllegalArgumentException e) {
                System.out.println("Investment account creation failed: " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }

        addAccount(newAccount);
        return accountNumber;
    }

    // Overloaded method for ChequeAccount with employer details
    public String createChequeAccount(String customerId, double initialDeposit, String employer, String employerAddress) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return null;
        }

        String accountNumber = "ACC" + String.format("%05d", accountCounter++);
        ChequeAccount newAccount = new ChequeAccount(accountNumber, initialDeposit, customerId, employer, employerAddress);

        addAccount(newAccount);
        return accountNumber;
    }

    // Monthly processing
    public void processMonthlyInterest() {
        for (Customer customer : customers.values()) {
            customer.applyMonthlyInterestToAllAccounts();
        }
    }
}