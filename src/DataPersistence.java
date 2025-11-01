import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataPersistence {
    private static final String CUSTOMERS_FILE = "customers.dat";
    private static final String ACCOUNTS_FILE = "accounts.dat";

    // Save all customers to file
    public static void saveCustomers(Map<String, Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMERS_FILE))) {
            // Convert to serializable format
            Map<String, SerializableCustomer> serializableCustomers = new HashMap<>();
            for (Customer customer : customers.values()) {
                serializableCustomers.put(customer.getCustomerId(), new SerializableCustomer(customer));
            }
            oos.writeObject(serializableCustomers);
            System.out.println("Customers saved successfully: " + customers.size() + " customers");
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    // Load all customers from file
    public static Map<String, Customer> loadCustomers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CUSTOMERS_FILE))) {
            @SuppressWarnings("unchecked")
            Map<String, SerializableCustomer> serializableCustomers = (Map<String, SerializableCustomer>) ois.readObject();

            Map<String, Customer> customers = new HashMap<>();
            for (SerializableCustomer sc : serializableCustomers.values()) {
                Customer customer = sc.toCustomer();
                customers.put(customer.getCustomerId(), customer);
            }
            System.out.println("Customers loaded successfully: " + customers.size() + " customers");
            return customers;
        } catch (FileNotFoundException e) {
            System.out.println("No existing customer data found. Starting fresh.");
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading customers: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Save all accounts to file
    public static void saveAccounts(Map<String, Account> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            // Convert to serializable format
            Map<String, SerializableAccount> serializableAccounts = new HashMap<>();
            for (Account account : accounts.values()) {
                serializableAccounts.put(account.getAccountNumber(), new SerializableAccount(account));
            }
            oos.writeObject(serializableAccounts);
            System.out.println("Accounts saved successfully: " + accounts.size() + " accounts");
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Load all accounts from file
    public static Map<String, Account> loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ACCOUNTS_FILE))) {
            @SuppressWarnings("unchecked")
            Map<String, SerializableAccount> serializableAccounts = (Map<String, SerializableAccount>) ois.readObject();

            Map<String, Account> accounts = new HashMap<>();
            for (SerializableAccount sa : serializableAccounts.values()) {
                Account account = sa.toAccount();
                accounts.put(account.getAccountNumber(), account);
            }
            System.out.println("Accounts loaded successfully: " + accounts.size() + " accounts");
            return accounts;
        } catch (FileNotFoundException e) {
            System.out.println("No existing account data found. Starting fresh.");
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Helper method to save both customers and accounts
    public static void saveAllData(Map<String, Customer> customers, Map<String, Account> accounts) {
        saveCustomers(customers);
        saveAccounts(accounts);
    }

    // Helper method to load both customers and accounts
    public static DataLoadResult loadAllData() {
        Map<String, Customer> customers = loadCustomers();
        Map<String, Account> accounts = loadAccounts();
        return new DataLoadResult(customers, accounts);
    }

    // Helper class to return both customers and accounts
    public static class DataLoadResult {
        public final Map<String, Customer> customers;
        public final Map<String, Account> accounts;

        public DataLoadResult(Map<String, Customer> customers, Map<String, Account> accounts) {
            this.customers = customers;
            this.accounts = accounts;
        }
    }
}

// Serializable wrapper for Customer
class SerializableCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customerId;
    private String phone;
    private String address;
    private String email;
    private String password;
    private String customerType;
    private Map<String, Object> additionalData;

    public SerializableCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.phone = customer.getPhone();
        this.address = customer.getAddress();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.customerType = customer.getCustomerType();
        this.additionalData = new HashMap<>();

        if (customer instanceof IndividualCustomer) {
            IndividualCustomer ind = (IndividualCustomer) customer;
            additionalData.put("firstName", ind.getFirstName());
            additionalData.put("lastName", ind.getLastName());
            additionalData.put("nationalId", ind.getNationalId());
        } else if (customer instanceof CompanyCustomer) {
            CompanyCustomer comp = (CompanyCustomer) customer;
            additionalData.put("companyName", comp.getCompanyName());
            additionalData.put("registrationNumber", comp.getRegistrationNumber());
            additionalData.put("contactPerson", comp.getContactPerson());
        }
    }

    public Customer toCustomer() {
        if ("Individual".equals(customerType)) {
            return new IndividualCustomer(
                    customerId,
                    (String) additionalData.get("firstName"),
                    (String) additionalData.get("lastName"),
                    (String) additionalData.get("nationalId"),
                    phone,
                    address,
                    email,
                    password
            );
        } else if ("Company".equals(customerType)) {
            return new CompanyCustomer(
                    customerId,
                    (String) additionalData.get("companyName"),
                    (String) additionalData.get("registrationNumber"),
                    (String) additionalData.get("contactPerson"),
                    phone,
                    address,
                    email,
                    password
            );
        }
        return null;
    }
}

// Serializable wrapper for Account
class SerializableAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private double balance;
    private String customerId;
    private String accountType;
    private Map<String, Object> additionalData;

    public SerializableAccount(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.customerId = account.getCustomerId();
        this.accountType = account.getAccountType();
        this.additionalData = new HashMap<>();

        if (account instanceof ChequeAccount) {
            ChequeAccount cheque = (ChequeAccount) account;
            additionalData.put("employer", cheque.getEmployer());
            additionalData.put("employerAddress", cheque.getEmployerAddress());
        }
    }

    public Account toAccount() {
        switch (accountType) {
            case "Savings":
                return new SavingsAccount(accountNumber, balance, customerId);
            case "Cheque":
                return new ChequeAccount(
                        accountNumber,
                        balance,
                        customerId,
                        (String) additionalData.get("employer"),
                        (String) additionalData.get("employerAddress")
                );
            case "Investment":
                return new InvestmentAccount(accountNumber, balance, customerId);
            default:
                return null;
        }
    }
}