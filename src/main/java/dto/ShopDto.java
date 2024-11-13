package dto;

public class ShopDto 
{
	private int id;
	private String name,address,vendoremail;
	public ShopDto() {
		super();
	}
	public ShopDto( String name, String address, String vendoremail) {
		super();
		
		this.name = name;
		this.address = address;
		this.vendoremail = vendoremail;
	}
	@Override
	public String toString() {
		return "ShopDto [id=" + id + ", name=" + name + ", address=" + address + ", vendoremail=" + vendoremail + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getVendoremail() {
		return vendoremail;
	}
	public void setVendoremail(String vendoremail) {
		this.vendoremail = vendoremail;
	}

}
