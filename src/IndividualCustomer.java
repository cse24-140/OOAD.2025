public class IndividualCustomer extends Customer {
    private String firstName;
    private String lastName;
    private String nationalId;
    private String password; // ✅ Added field

    public IndividualCustomer(String customerId, String firstName, String lastName,
                              String nationalId, String phone, String address,
                              String email, String password) { // ✅ Constructor updated
        super(customerId, phone, address, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.password = password; // ✅ Store password
    }

    // ✅ Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getCustomerType() {
        return "Individual";
    }
}