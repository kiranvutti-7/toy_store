package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

import dto.VendorDto;

public class VendorDao {

    // Create connection to the database
    public Connection createVendorConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/toystoredb?createDatabaseIfNotExist=true", 
            "root", 
            "kiran"
        );
    }

    // Create the vendor table if not exists
    public void createVendorTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS vendor ("
            + "id INT PRIMARY KEY AUTO_INCREMENT, "
            + "name VARCHAR(45), "
            + "email VARCHAR(45) UNIQUE, "
            + "password VARCHAR(45), "
            + "wallet DOUBLE, "
            + "phone BIGINT(10), "
            + "address VARCHAR(45))";
        try (Connection con = createVendorConnection(); 
             PreparedStatement ps = con.prepareStatement(createTableSQL)) {
            ps.execute();
        }
    }

    
    
    
    
    
    // Save vendor details
    public void saveVendorDetails(VendorDto vdto) throws SQLException {
        String insertSQL = "INSERT INTO vendor (name, email, password, wallet, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = createVendorConnection(); 
             PreparedStatement ps = con.prepareStatement(insertSQL)) {
            ps.setString(1, vdto.getName());
            ps.setString(2, vdto.getEmail());
            ps.setString(3, vdto.getPassword()); 
            ps.setDouble(4, vdto.getWallet());
            ps.setLong(5, vdto.getPhone());
            ps.setString(6, vdto.getAddress());
            ps.executeUpdate();
        }
    }

    // Check if the vendor email is already registered
    public boolean validVendorRegister(String email) throws SQLException {
        String checkEmailSQL = "SELECT COUNT(*) FROM vendor WHERE email = ?";
        try (Connection con = createVendorConnection();
             PreparedStatement ps = con.prepareStatement(checkEmailSQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        }
        return false; 
    }

    // Validate vendor login credentials
    public boolean validVendorLogin(String email, String password) throws SQLException {
        String loginSQL = "SELECT password FROM vendor WHERE email = ?";
        try (Connection con = createVendorConnection();
             PreparedStatement ps = con.prepareStatement(loginSQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password); 
            }
        }
        return false; 
    }

    // Update vendor profile (address and email)
    public void updateVendorProfile(VendorDto vdto) throws SQLException {
        
        try (Connection con = createVendorConnection(); 
             PreparedStatement ps = con.prepareStatement("UPDATE vendor SET address = ? WHERE email = ?")) {
            ps.setString(1, vdto.getAddress());
            ps.setString(2, vdto.getEmail());
            ps.executeUpdate();
        }
    }

    // Delete vendor profile based on email
    public void deleteVendorProfile(VendorDto vdto) throws SQLException {
        
        try (Connection con = createVendorConnection(); 
             PreparedStatement ps = con.prepareStatement( "DELETE FROM vendor WHERE email = ?")) {
            ps.setString(1, vdto.getEmail());
            ps.executeUpdate();
        }
    }

    // Fetch vendor profile based on email
    public void fetchVendorProfile(VendorDto vdto) throws SQLException {
       
        try (Connection con = createVendorConnection(); 
             PreparedStatement ps = con.prepareStatement("SELECT * FROM vendor WHERE email = ?")) {
            ps.setString(1, vdto.getEmail());
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                System.out.println(res.getInt("id"));
                System.out.println(res.getString("name"));
                System.out.println(res.getString("email"));
                System.out.println(res.getString("password"));
                System.out.println(res.getDouble("wallet"));
                System.out.println(res.getString("address"));
            } else {
                System.out.println("Details not found.");
            }
        }
    }
    
    public void vendorProfile() throws SQLException
    {
    	VendorDto vdto=new VendorDto();
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Welcome to Vendor Profile..... \n please select the below options \n1.update\n2.delete\n3.fetch\4.Exit");
    	int n=sc.nextInt();
    	switch(n)
    	{
    	case 1:
    	{
    		
    		updateVendorProfile(vdto);
    	}
    	case 2:
    	{
    		deleteVendorProfile(vdto);
    	}
    	case 3:{
    		fetchVendorProfile(vdto);
    	}
    	case 4:
    	{
    		System.out.println("Thnaks for visiting❤️❤️");
    	}
    	default :{
    		System.out.println("Invalid Option");
    	}
    	}
    }
}
