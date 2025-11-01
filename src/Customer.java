import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String customerId;
    protected String phone;
    protected String address;
    protected String email;
    protected String password;
    protected List<Account> accounts;

    public Customer(String customerId, String phone, String address, String email, String password) {
        this.customerId = customerId;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    // Getters
    public String getCustomerId() { return customerId; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<Account> getAccounts() { return accounts; }

    // Setters
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // Account Management
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public double getTotalBalance() {
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    public void applyMonthlyInterestToAllAccounts() {
        for (Account account : accounts) {
            account.applyMonthlyInterest();
        }
    }

    public abstract String getCustomerType();
}