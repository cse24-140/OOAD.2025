public class CompanyCustomer extends Customer {
    private String companyName;
    private String registrationNumber;
    private String contactPerson;

    public CompanyCustomer(String customerId, String companyName, String registrationNumber,
                           String contactPerson, String phone, String address, String email, String password) {
        super(customerId, phone, address, email, password);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.contactPerson = contactPerson;
    }

    // Getters
    public String getCompanyName() { return companyName; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getContactPerson() { return contactPerson; }

    @Override
    public String getCustomerType() {
        return "Company";
    }
}