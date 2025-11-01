public class IndividualCustomer extends Customer {
    private String firstName;
    private String lastName;
    private String nationalId;

    public IndividualCustomer(String customerId, String phone, String address,
                              String firstName, String lastName, String nationalId) {
        super(customerId, phone, address);
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

}
