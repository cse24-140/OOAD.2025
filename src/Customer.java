   public abstract class Customer {
        protected String customerId;
        protected String phone;
        protected String address;
    
        public Customer(String customerId, String phone, String address) {
            this.customerId = customerId;
            this.phone = phone;
            this.address = address;
        }
    
        
        public String getCustomerId() { 
            return customerId;
         }

        public String getPhone() {
             return phone;
             }

        public String getAddress() {
             return address;
             }
             
    }
    

