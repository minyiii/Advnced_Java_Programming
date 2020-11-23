import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentQueries {
	private static final String URL = "jdbc:mysql://localhost/member?serverTimezone=UTC";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";
	
	private Connection conn;
	private PreparedStatement selectAllPeople;
	private PreparedStatement selectByName;
	private PreparedStatement updateByID;
	private PreparedStatement deleteByID;
	private PreparedStatement insertNewPeople;
	
	//constructor
	public StudentQueries() {
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			selectAllPeople = conn.prepareStatement("SELECT * FROM people");
			selectByName = conn.prepareStatement("SELECT * FROM people WHERE name = ?");
			updateByID = conn.prepareStatement("Update member.people SET name = ?,  type = ?, phone = ? WHERE MemberID = ?");
			deleteByID = conn.prepareStatement("DELETE FROM people WHERE MemberID = ?");
			insertNewPeople = conn.prepareStatement("INSERT INTO people (name, type, phone) VALUES (?,?,?)");
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		}
	}
	
	//取得所有資料
	public List<Student> getAllStudent(){
		List<Student> resultList = null;
		ResultSet rs = null;
		
		try {
			rs = selectAllPeople.executeQuery();
			resultList = new ArrayList<Student>();
			
			while(rs.next()) {
				resultList.add(new Student(
					rs.getInt("MemberID"),
					rs.getString("name"),
					rs.getString("type"),
					rs.getString("phone")));
			}
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		finally {
			try {
				rs.close();
			}
			catch(SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return resultList;
	}
	
	//依照名字取得該筆資料
	public Student getByName(String name) {
		Student student = new Student();
		ResultSet rs = null;
		
		try {
			selectByName.setString(1, name);
			rs = selectByName.executeQuery();
			
			while(rs.next()) {
				student.setMemberID(rs.getInt("MemberID"));
				student.setName(name);
				student.setType(rs.getString("type"));
				student.setPhone(rs.getString("phone"));
			}
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
			close();
		}
		return student;
	}
	
	//新增一筆資料
	public int addStudent(String name, String type, String phone) {
		int result = 0;
		
		try {
			insertNewPeople.setString(1, name);
			insertNewPeople.setString(2, type);
			insertNewPeople.setString(3, phone);
			result = insertNewPeople.executeUpdate();
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
			close();
		}
		return result;
	}
	
	//更新資料
	public int updateStudent(int id, String name, String type, String phone) {
		int result = 0;
		
		try {
			updateByID.setString(1, name);
			updateByID.setString(2, type);
			updateByID.setString(3, phone);
			updateByID.setInt(4, id);
			result = updateByID.executeUpdate();
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
			close();
		}
		return result;
	}
	
	//刪除
	public int deleteStudent(int memberID) {
		int result = 0;
		try {
			deleteByID.setInt(1, memberID);
			result = deleteByID.executeUpdate();
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
			close();
		}
		return result;
	}
	
	//
	public void close() {
		try {
			conn.close();
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
