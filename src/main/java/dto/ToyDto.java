package dto;

public class ToyDto 
{
 private int toyid;
 private String toybrand, toytype, toycolour;
 private double toycost;
@Override
public String toString() {
	return "ToyDto [toyid=" + toyid + ", toybrand=" + toybrand + ", toytype=" + toytype + ", toycolour=" + toycolour
			+ ", toycost=" + toycost + "]";
}
public int getToyid() {
	return toyid;
}
public void setToyid(int toyid) {
	this.toyid = toyid;
}
public String getToybrand() {
	return toybrand;
}
public void setToybrand(String toybrand) {
	this.toybrand = toybrand;
}
public String getToytype() {
	return toytype;
}
public void setToytype(String toytype) {
	this.toytype = toytype;
}
public String getToycolour() {
	return toycolour;
}
public void setToycolour(String toycolour) {
	this.toycolour = toycolour;
}
public double getToycost() {
	return toycost;
}
public void setToycost(double toycost) {
	this.toycost = toycost;
}
public ToyDto() {
	super();
}
public ToyDto( String toybrand, String toytype, String toycolour, double toycost) {
	super();
	this.toybrand = toybrand;
	this.toytype = toytype;
	this.toycolour = toycolour;
	this.toycost = toycost;
}
}
