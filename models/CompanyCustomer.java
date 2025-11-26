package models;

public class CompanyCustomer extends Customer {

    private String companyName;
    private String companyReg;
    private String contactFirst;
    private String contactLast;
    private String position;
    private String taxNumber;
    private String username;  // added for consistency

    public CompanyCustomer(
            String customerId,
            String companyName,
            String companyReg,
            String contactFirst,
            String contactLast,
            String phone,
            String address,
            String position,
            String taxNumber,
            String email,
            String password,
            String username
    ) {
        super(customerId, phone, address, email, password);

        this.companyName = companyName;
        this.companyReg = companyReg;
        this.contactFirst = contactFirst;
        this.contactLast = contactLast;
        this.position = position;
        this.taxNumber = taxNumber;
        this.username = username;
    }

    public String getCompanyName() { return companyName; }
    public String getCompanyReg() { return companyReg; }
    public String getContactFirstName() { return contactFirst; }
    public String getContactLastName() { return contactLast; }
    public String getPosition() { return position; }
    public String getTaxNumber() { return taxNumber; }
    public String getUsername() { return username; }

    @Override
    public String getName() {
        return companyName;
    }

    @Override
    public String getCustomerType() {
        return "company";
    }
}
