package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

import dto.ShopDto;
import dto.ToyDto;
import dto.VendorDto;

public class VendorShopDao {

    // Create a connection to the database
    public Connection createShopConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/toystoredb?createDatabaseIfNotExist=true", 
            "root", 
            "kiran"
        );
    }
    
    
    


    // Create the shop table if it doesn't exist
    public void createShopTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS shop ("
            + "id INT PRIMARY KEY AUTO_INCREMENT, "
            + "name VARCHAR(45), "
            + "address VARCHAR(45), "
            + "vendoremail VARCHAR(45))";
        try (Connection con = createShopConnection(); 
             PreparedStatement ps = con.prepareStatement(createTableSQL)) {
            ps.execute();
        }
    }

    // Save a new shop to the database (insert)
    public void saveShop(ShopDto sdto) throws SQLException {
        String insertSQL = "INSERT INTO shop (name, address, vendoremail) VALUES (?, ?, ?)";
        try (Connection con = createShopConnection(); 
             PreparedStatement ps = con.prepareStatement(insertSQL)) {
            ps.setString(1, sdto.getName());
            ps.setString(2, sdto.getAddress());
            ps.setString(3, sdto.getVendoremail());
            ps.executeUpdate();
        }
    }

    // Delete a shop based on the vendor's email
    public void deleteShop(ShopDto sdto) throws SQLException {
        String deleteSQL = "DELETE FROM shop WHERE vendoremail = ?";
        try (Connection con = createShopConnection(); 
             PreparedStatement ps = con.prepareStatement(deleteSQL)) {
            ps.setString(1, sdto.getVendoremail());
            ps.executeUpdate();
        }
    }
    
    
    public void updateShop(ShopDto sdto) throws SQLException
    {
    	String updateshop="update email,name,address(?,?,?) from shop where email=?";
    	 try (Connection con = createShopConnection(); 
                 PreparedStatement ps = con.prepareStatement(updateshop)) {
             ps.setString(1, sdto.getVendoremail());
             ps.setString(2, sdto.getName());
             ps.executeUpdate();
         }
    }


    
    
    public void Shop() throws SQLException
    {
    	ToyDto tdto=new ToyDto();
    	ToyDao tdao=new ToyDao();
    	ShopDto sdto=new ShopDto();
    	VendorDto vdto=new VendorDto();
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Welcome To VendorShop..... \n please select the below options \n1.create\n2.update\n3.delete\4.addToys\n5.updateToys\n6.deleteToys\n7.Exit");
    	int n=sc.nextInt();
    	switch(n)
    	{
    	case 1:
    	{
    		saveShop(sdto);
    		
    	}
    	case 2:
    	{
    		updateShop(sdto);
    	}
    	case 3:{
    		deleteShop(sdto);
    	}
    	case 4:
    	{
    		tdao.saveToyDetails(tdto);
    	}
    	case 5:{
    		tdao.updateToy(tdto);
    	}
    	case 6:{
    		tdao.deleteToy(tdto);
    	}
    	case 7:
    	{
    		System.out.println("Thnaks for visiting❤️❤️");
    	}
    	default :{
    		System.out.println("Invalid Option");
    	}
    	}
    
    }
}
