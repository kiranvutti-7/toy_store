package controller;

import java.sql.SQLException;
import java.util.Scanner;

import dao.CustomerDao;
import dao.ToyDao;
import dao.VendorDao;
import dao.VendorShopDao;
import dto.CustomerDto;
import dto.ToyDto;
import dto.VendorDto;

public class ToyStoreController {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        System.out.println("-------------------Welcome To ToyStore---------------");
        boolean start = true;
        
        while (start) {
            System.out.println("1.Customer\n2.Vendor\n3.Exit\nPlease choose the option for further process...");
            int n = sc.nextInt();
            
            switch (n) {
                case 1:
                    customerOptions();
                    break;
                case 2:
                    vendorOptions();
                    break;
                case 3:
                    start = false;
                    System.out.println(".....Thanks for visiting the ToyStore. Please do visit again.... ^-*");
                    break;
                default:
                    System.out.println("Please Enter a valid option...");
                    break;
            }
        }
        sc.close();
    }

    // Customer Section
    public static void customerOptions() throws SQLException {
        System.out.println("Welcome to ToyStore CustomerZone....\n1.Register\n2.Login\n3.Exit\nPlease select an option...");
        
        int n = sc.nextInt();
        switch (n) {
            case 1:
                registrationValidation();
                System.out.println("Do you want to login (yes/no)?");
                String ch = sc.next();
                if (ch.equalsIgnoreCase("yes")) {
                    loginValidation();
                }
                break;
            case 2:
                loginValidation();
                break;
            case 3:
                System.out.println(".....Thanks for visiting the ToyStore. Please do visit again.... ^-*");
                break;
            default:
                System.out.println("Please Enter a valid option.");
        }
    }

    private static void registrationValidation() throws SQLException {
        CustomerDao cdao = new CustomerDao();
        System.out.println("Enter your name:");
        String name = sc.next();
        System.out.println("Enter your email:");
        String email = sc.next();  
        
        if (cdao.validRegister(email)) {
            System.out.println("User already exists! You can directly login or register with a new email.");
            System.out.println("Want to register with a new email? (yes/no)");
            String ch = sc.next();
            if (ch.equalsIgnoreCase("yes")) {
                // Use loop instead of recursion to avoid stack overflow
                registrationValidation(); 
            } else {
                loginValidation();
            }
        } else {
            System.out.println("Enter Password:");
            String password = sc.next();
            System.out.println("Enter Wallet balance:");
            Double wallet = sc.nextDouble();
            System.out.println("Enter your Mobile Number:");
            Long phone = sc.nextLong();
            System.out.println("Enter Address:");
            String address = sc.next();
            cdao.saveCustomerDetails(new CustomerDto(email, password, name, phone, wallet, address));
            System.out.println("Registration successful! You have received Rs.500 as a welcome bonus.");
        }
    }

    private static void loginValidation() throws SQLException {
        CustomerDao cdao = new CustomerDao();
        System.out.println("Enter EmailId:");
        String email = sc.next();
        System.out.println("Enter Password:");
        String password = sc.next();
        cdao.validLogin(email, password);

        System.out.println("Welcome to the ToyStore!\n\nChoose below options:\n1.Shopping\n2.Profile\n3.Exit");
        int n = sc.nextInt();
        switch (n) {
            case 1:
                shoppingOptions();
                break;
            case 2:
            	cdao.customerProfile();
                break;
            case 3:
                System.out.println("Thanks for visiting!");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void shoppingOptions() throws SQLException {
        ToyDao tdao = new ToyDao();
        ToyDto tdto = new ToyDto();
        CustomerDao cdao=new CustomerDao(); 
        System.out.println("Welcome to the Shopping Zone!\n1.Browse Toys\n2.Cost\n3.Payment\n4.Exit");
        int n = sc.nextInt();
        switch (n) {
            case 1:
                tdao.browseToys();  
                break;
            case 2:
                System.out.println("Enter Toy Brand: ");
                String toybrand = sc.next();
                tdto.setToybrand(toybrand); 
                tdao.costOfToy(tdto);
                break;
            case 3:
            	
            	cdao.processPayment();
                break;
            case 4:
                System.out.println("Thanks for visiting the ToyStore.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    

    
    
    
    
    

    // Vendor Section
    public static void vendorOptions() throws SQLException {
        System.out.println("Welcome to ToyStore VendorZone....\n1.Register\n2.Login\n3.Exit");
        int n = sc.nextInt();
        switch (n) {
            case 1:
                registrationVendorValidation();
                System.out.println("Do you want to login (yes/no)?");
                String ch = sc.next();
                if (ch.equalsIgnoreCase("yes")) {
                    loginVendorValidation();
                }
                break;
            case 2:
                loginVendorValidation();
                break;
            case 3:
                System.out.println(".....Thanks for visiting the ToyStore. Please do visit again.... ^-*");
                break;
            default:
                System.out.println("Please Enter a valid option.");
        }
    }

    private static void registrationVendorValidation() throws SQLException {
        VendorDao vdao = new VendorDao();
        System.out.println("Enter your name:");
        String name = sc.next();
        System.out.println("Enter your email:");
        String email = sc.next();
        
        if (vdao.validVendorRegister(email)) {
            System.out.println("Vendor already exists! You can directly login or register with a new email.");
            System.out.println("Want to register with a new email? (yes/no)");
            String ch = sc.next();
            if (ch.equalsIgnoreCase("yes")) {
                registrationVendorValidation();  // Avoid recursion, use loop instead
            } else {
                loginVendorValidation();
            }
        } else {
            System.out.println("Enter Password:");
            String password = sc.next();
            System.out.println("Enter Wallet balance:");
            Double wallet = sc.nextDouble();
            System.out.println("Enter your Mobile Number:");
            Long phone = sc.nextLong();
            System.out.println("Enter Address:");
            String address = sc.next();
            vdao.saveVendorDetails(new VendorDto(email, password, name, phone, wallet, address));
            System.out.println("Registration successful!");
        }
    }

    private static void loginVendorValidation() throws SQLException {
    	 VendorShopDao sdao=new VendorShopDao();
        VendorDao vdao = new VendorDao();
        System.out.println("Enter Vendor EmailId:");
        String email = sc.next();
        System.out.println("Enter Vendor Password:");
        String password = sc.next();
        vdao.validVendorLogin(email, password);

        System.out.println("Welcome to Vendor Zone!\nChoose below options:\n1.Shop\n2.Profile\n3.Exit");
        int n = sc.nextInt();
        switch (n) {
            case 1:
               sdao.Shop(); 
            	
                break;
            case 2:
                // Handle vendor profile (to be implemented)
            	vdao.vendorProfile();
                break;
            case 3:
                System.out.println("Thanks for visiting the ToyStore.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    

}
