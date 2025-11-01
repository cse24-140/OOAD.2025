public class IndividualCustomer extends Customer {
    private String firstName;
    private String lastName;
    private String nationalId;

    public IndividualCustomer(String customerId, String firstName, String lastName,
                              String nationalId, String phone, String address,
                              String email, String password) {
        super(customerId, phone, address, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getNationalId() { return nationalId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    @Override
    public String getCustomerType() {
        return "Individual";
    }
}