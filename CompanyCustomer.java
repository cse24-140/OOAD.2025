public class CompanyCustomer extends Customer {
        private String companyName;
        private String registrationNumber; 
        private String contactPerson; 
    
        public CompanyCustomer(String customerId, String phone, String address,
                               String companyName, String registrationNumber, String contactPerson) {
            super(customerId, phone, address);
            this.companyName = companyName;
            this.registrationNumber = registrationNumber;
            this.contactPerson = contactPerson;
        }
    
        
        public String getCompanyName() { 
            return companyName;
         }

        public String getRegistrationNumber() {
             return registrationNumber; 
            }

        public String getContactPerson() {
             return contactPerson;
             }
    }
    

