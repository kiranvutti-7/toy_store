package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;
import dto.ToyDto;

public class ToyDao {

    // Method to create a connection with the database
    public Connection createToyConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/toystoredb", "root", "kiran");
    }

    // Method to save toy details into the database
    public void saveToyDetails(ToyDto tdto) throws SQLException {
        String query = "INSERT INTO toy (toybrand, toytype, toycolour, toycost) VALUES (?, ?, ?, ?)";
        
        try (Connection con = createToyConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, tdto.getToybrand());
            ps.setString(2, tdto.getToytype());
            ps.setString(3, tdto.getToycolour());
            ps.setDouble(4, tdto.getToycost());

            ps.executeUpdate();
            System.out.println("Toy added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while saving toy details.", e);
        }
    }

    // Method to update the cost of a toy
    public void updateToy(ToyDto tdto) throws SQLException {
        
        
        try (Connection con = createToyConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE toy SET toycost = ? WHERE toytype = ?")) {

            ps.setDouble(1, tdto.getToycost());
            ps.setString(2, tdto.getToytype());

            ps.executeUpdate();
            System.out.println("Toy updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating toy.", e);
        }
    }

    // Method to delete a toy by its brand
    public void deleteToy(ToyDto tdto) throws SQLException {
        
        
        try (Connection con = createToyConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM toy WHERE toybrand = ?")) {

            ps.setString(1, tdto.getToybrand());
            ps.executeUpdate();
            System.out.println("Toy deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while deleting toy.", e);
        }
    }

    // Method to browse all toys by their brand
    public void browseToys() throws SQLException {
       Scanner scanner=new Scanner(System.in);
        boolean browsing = true;

        try (Connection con = createToyConnection();
             PreparedStatement ps = con.prepareStatement("SELECT toybrand FROM toy");
             ResultSet res = ps.executeQuery()) {

            System.out.println("Available Toy Brands:");
            
            // Display the available toy brands
            while (res.next()) {
                String toyBrand = res.getString("toybrand");
                System.out.println(toyBrand);
            }

            // Loop to keep offering the options to the user
            while (browsing) {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Add to Cart");
                System.out.println("2. Exit");
                System.out.print("Please choose an option (1/2): ");
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:

                        System.out.print("Enter the toy brand you want to add to the cart: ");
                        scanner.nextLine();
                        String toyToAdd = scanner.nextLine();
                        
                        
                        addToCart(toyToAdd);
                        break;

                    case 2:
                        // Exit browsing
                        System.out.println("Exiting the toy browsing.");
                        browsing = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please choose again.");
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching toy brands.", e);
        }
    }

//add to cart    
    private void addToCart(String toyBrand) {
        System.out.println("Toy '" + toyBrand + "' added to your cart.");
       
    }
    
    
    // Method to get the cost of a toy based on the brand
    public void costOfToy(ToyDto tdto) throws SQLException {
        
        
        try (Connection con = createToyConnection();
             PreparedStatement ps = con.prepareStatement("SELECT toycost FROM toy WHERE toybrand = ?")) {

            ps.setString(1, tdto.getToybrand());

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    double toyCost = res.getDouble("toycost");
                    System.out.println("The cost of " + tdto.getToybrand() + " is Rs. " + toyCost);
                } else {
                    System.out.println("No toy found with the given brand.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching toy cost.", e);
        }
    }
    
    
    public double getToyCost(String toyBrand) throws SQLException {
        double cost = 0;
        Connection con = createToyConnection();
        PreparedStatement ps = con.prepareStatement("SELECT toycost FROM toy WHERE toybrand = ?");
        ps.setString(1, toyBrand);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            cost = rs.getDouble("toycost");
        }
        
        rs.close();
        ps.close();
        con.close();
        return cost;
    }

    // Update customer's wallet balance after payment
    public void updateCustomerWallet(String email, double newBalance) throws SQLException {
        Connection con = createToyConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE customer SET wallet = ? WHERE email = ?");
        ps.setDouble(1, newBalance);
        ps.setString(2, email);
        ps.executeUpdate();
        
        ps.close();
        con.close();
    }
}
