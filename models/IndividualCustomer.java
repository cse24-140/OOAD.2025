package models;

public class IndividualCustomer extends Customer {

    private String firstName;
    private String lastName;
    private String nationalId;
    private String gender;
    private String username;

    // ============================================================
    // FULL 9-ARG CONSTRUCTOR (MATCHES BankingSystem)
    // ============================================================
    public IndividualCustomer(
            String customerId,
            String firstName,
            String lastName,
            String nationalId,
            String phone,
            String address,
            String email,
            String password,
            String username
    ) {
        super(customerId, phone, address, email, password);

        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.username = username;
        this.gender = "";
    }

    // ============================================================
    // OPTIONAL CONSTRUCTOR INCLUDING GENDER (for SignUp)
    // ============================================================
    public IndividualCustomer(
            String customerId,
            String firstName,
            String lastName,
            String nationalId,
            String gender,
            String phone,
            String address,
            String email,
            String password,
            String username
    ) {
        super(customerId, phone, address, email, password);

        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.gender = gender;
        this.username = username;
    }

    // ============================================================
    // GETTERS
    // ============================================================
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getNationalId() { return nationalId; }
    public String getGender() { return gender; }
    public String getUsername() { return username; }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getCustomerType() {
        return "Individual";
    }
}
