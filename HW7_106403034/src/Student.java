
public class Student {
	private int memberID;
	private String name;
	private String type;
	private String phone;
	
	//constructor
	public Student() {
	}
	
	//constructor
	public Student(int memberID, String name, String type, String phone) {
		this.memberID = memberID;
		this.name = name;
		this.type = type;
		this.phone = phone;
	}
	
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	
	public int getMemberID() {
		return memberID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
}