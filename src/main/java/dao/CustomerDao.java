package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;
import dto.CustomerDto;

public class CustomerDao {
    
    // Create a connection to the database
    public Connection createCustomerConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/toystoredb?createDatabaseIfNotExist=true", 
            "root", 
            "kiran"
        );
    }

    // Create the customer table if it doesn't exist
    public void createCustomerTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS customer ("
            + "id INT PRIMARY KEY AUTO_INCREMENT, "
            + "name VARCHAR(45), "
            + "email VARCHAR(45) UNIQUE, "
            + "password VARCHAR(45), "
            + "wallet DOUBLE, "
            + "phone BIGINT(10), "
            + "address VARCHAR(45))";
        try (Connection con = createCustomerConnection(); 
             PreparedStatement ps = con.prepareStatement(createTableSQL)) {
            ps.execute();
        }
    }

    // Save customer details to the database
    public void saveCustomerDetails(CustomerDto cdto) throws SQLException {
        
        try (Connection con = createCustomerConnection(); 
             PreparedStatement ps = con.prepareStatement("INSERT INTO customer (name, email, password, wallet, phone, address) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, cdto.getName());
            ps.setString(2, cdto.getEmail());
            ps.setString(3, cdto.getPassword());
            ps.setDouble(4, cdto.getWallet());
            ps.setLong(5, cdto.getPhone());
            ps.setString(6, cdto.getAddress());
            ps.executeUpdate();
        }
    }

    // Check if the email is already registered (validate register)
    public boolean validRegister(String email) throws SQLException {
        
        try (Connection con = createCustomerConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM customer WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        }
        return false; 
    }

    // Validate user login credentials
    public boolean validLogin(String email, String password) throws SQLException {
        
        try (Connection con = createCustomerConnection();
             PreparedStatement ps = con.prepareStatement("SELECT password FROM customer WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password); 
            }
        }
        return false; 
    }
    
    public double getWalletBalance(String email) throws SQLException {
        double walletBalance = 0;
        Connection con = createCustomerConnection();
        PreparedStatement ps = con.prepareStatement("SELECT wallet FROM customer WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            walletBalance = rs.getDouble("wallet");
        }
        
        rs.close();
        ps.close();
        con.close();
        
        return walletBalance;
    }
    
    
    public void customerProfile() throws SQLException {
        Scanner scanner = new Scanner(System.in); 

        
        System.out.print("Enter your email: ");
        String customerEmail = scanner.nextLine();

   
        CustomerDto customer = fetchCustomerProfileByEmail(customerEmail);
        if (customer == null) {
            System.out.println("Customer profile not found with the given email.");
            return;
        }

        // Show the current customer profile info
        System.out.println("Current Profile Info:");
        System.out.println(customer.toString());

        boolean profileActive=true;
        while (profileActive) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Update Profile");
            System.out.println("2. Delete Account");
            System.out.println("3. Exit");
            System.out.print("Please choose an option (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left over

            switch (choice) {
                case 1:
                    // Update profile option
                    updateProfile(customerEmail, scanner);
                    break;

                case 2:
                    // Delete account option
                    deleteAccount(customerEmail);
                    profileActive = false;
                    break;

                case 3:
               
                    System.out.println("Exiting the customer profile.");
                    profileActive = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }

       
    }

    // Fetch the current profile by email from the database
    private CustomerDto fetchCustomerProfileByEmail(String email) {
        CustomerDto customer = null;

        try (Connection con = createCustomerConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM customer WHERE email = ?")) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new CustomerDto();
                    customer.setId(rs.getInt("id"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password")); 
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getLong("phone"));
                    customer.setWallet(rs.getDouble("wallet"));
                    customer.setAddress(rs.getString("address"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    
    private void updateProfile(String email, Scanner scanner) throws SQLException {
        System.out.println("\nWhich field would you like to update?");
        System.out.println("1. Update Name");
        System.out.println("2. Update Email");
        System.out.println("3. Update Address");
        System.out.println("4. Update Phone");
        System.out.println("5. Update Wallet Balance");
        System.out.print("Please choose an option (1-5): ");
        int updateChoice = scanner.nextInt();
        scanner.nextLine();

       
        CustomerDto customer = fetchCustomerProfileByEmail(email);
        if (customer == null) {
            System.out.println("Customer profile not found. Cannot update.");
            return;
        }

        switch (updateChoice) {
            case 1:
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                customer.setName(newName);
                break;

            case 2:
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                customer.setEmail(newEmail);
                break;

            case 3:
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                customer.setAddress(newAddress);
                break;

            case 4:
                System.out.print("Enter new phone number: ");
                long newPhone = scanner.nextLong();
                customer.setPhone(newPhone);
                break;

            case 5:
                System.out.print("Enter new wallet balance: ");
                double newWallet = scanner.nextDouble();
                customer.setWallet(newWallet);
                break;

            default:
                System.out.println("Invalid choice. No changes made.");
                return;
        }

       
        updateCustomerProfileInDatabase(customer);
        System.out.println("Profile updated successfully.");
    }

    
    private void updateCustomerProfileInDatabase(CustomerDto customer) throws SQLException {
        String updateQuery = "UPDATE customers SET email = ?, password = ?, name = ?, phone = ?, wallet = ?, address = ? WHERE email = ?";
        try (Connection con = createCustomerConnection();
             PreparedStatement ps = con.prepareStatement(updateQuery)) {

            ps.setString(1, customer.getEmail());
            ps.setString(2, customer.getPassword()); // In real apps, hash the password before saving
            ps.setString(3, customer.getName());
            ps.setLong(4, customer.getPhone());
            ps.setDouble(5, customer.getWallet());
            ps.setString(6, customer.getAddress());
            ps.setString(7, customer.getEmail());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Failed to update customer profile.");
            }
        }
    }

    // Delete the customer account using the email
    private void deleteAccount(String email) throws SQLException {
        System.out.println("Are you sure you want to delete your account? This action cannot be undone.");
        System.out.println("1. Yes, delete my account");
        System.out.println("2. No, cancel");
        System.out.print("Please choose an option (1/2): ");

        Scanner scanner = new Scanner(System.in);
        int deleteChoice = scanner.nextInt();

        if (deleteChoice == 1) {
            try (Connection con = createCustomerConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM customer WHERE email = ?")) {

                ps.setString(1, email);
                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Your account has been deleted successfully.");
                } else {
                    System.out.println("Failed to delete your account.");
                }
            }
        } else {
            System.out.println("Account deletion canceled.");
        }
    }


    
    
    
    //Payment Process
    public static void processPayment() throws SQLException {
    	Scanner sc=new Scanner(System.in);
        ToyDao tdao = new ToyDao();
        CustomerDao cdao = new CustomerDao();

        System.out.println("Enter your EmailId: ");
        String email = sc.next();

        System.out.println("Enter Toy Brand to purchase: ");
        String toybrand = sc.next();
        double toyCost = tdao.getToyCost(toybrand);


        double walletBalance = cdao.getWalletBalance(email);

        if (walletBalance >= toyCost) {
           
            double newBalance = walletBalance - toyCost;
            
  
            tdao.updateCustomerWallet(email, newBalance);
            
            System.out.println("Payment Successful! Your new wallet balance is: Rs." + newBalance);
        } else {
            // Insufficient funds
            System.out.println("Insufficient funds! Please add money to your wallet.");
        }
    }
    
}                                                                                