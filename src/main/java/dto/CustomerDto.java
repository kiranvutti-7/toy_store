package dto;

public class CustomerDto 
{
	private int id;
	private String email,address,password,name;
	private long phone;
	private double wallet;
	public CustomerDto() {
		super();
	}
	public CustomerDto( String email, String password, String name, long phone, double wallet,String address) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.wallet = wallet;
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", email=" + email + ", address=" + address + ", password=" + password
				+ ", name=" + name + ", phone=" + phone + ", wallet=" + wallet + "]";
	}

	

}
