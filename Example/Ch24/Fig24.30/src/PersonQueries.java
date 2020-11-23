import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//像是SA的Helper
public class PersonQueries {
	private static final String URL = "jdbc:mysql://localhost/addressbook?serverTimezone=UTC";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";
	
	private Connection connection;
	private PreparedStatement selectAllPeople;
	private PreparedStatement selectPeopleByLastName;
	private PreparedStatement insertNewPerson;
	
	//constructor
	public PersonQueries() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			selectAllPeople = connection.prepareStatement("SELECT * FROM addresses");
			selectPeopleByLastName = connection.prepareStatement(
					"SELECT * FROM addresses WHERE LastName=?");
			insertNewPerson = connection.prepareStatement(
					"INSERT INTO addresses (FirstName, LastName, Email, PhoneNumber)"
					+ "VALUES (?,?,?,?)");
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		}
	}
	
	public List<Person> getAllPeople(){
		List<Person> results = null;
		ResultSet resultSet = null;
		try {
			resultSet = selectAllPeople.executeQuery();
			results = new ArrayList<Person>();
			while(resultSet.next()) {
				results.add(new Person(
					resultSet.getInt("AddressID"),
					resultSet.getString("FirstName"),
					resultSet.getString("LastName"),
					resultSet.getString("Email"),
					resultSet.getString("PhoneNumber")
				));
			}
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		finally {
			try {
				resultSet.close();
			}
			catch(SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}
	
	public List<Person> getPersonByLastName(String name){
		List<Person> results = null;
		ResultSet resultSet = null;
		try {
			selectPeopleByLastName.setString(1, name);
			resultSet = selectPeopleByLastName.executeQuery();
			results = new ArrayList<Person>();
			
			while(resultSet.next()) {
				results.add(new Person(
					resultSet.getInt("AddressID"),
					resultSet.getString("FirstName"),
					resultSet.getString("LastName"),
					resultSet.getString("Email"),
					resultSet.getString("PhoneNumber")
				));
			}
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		finally {
			try {
				resultSet.close();
			}
			catch(SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}
	
	public int addPerson(String fname, String lname, String email, String num) {
		int result = 0;
		try {
			insertNewPerson.setString(1, fname);
			insertNewPerson.setString(2, lname);
			insertNewPerson.setString(3, email);
			insertNewPerson.setString(4, num);
			
			result = insertNewPerson.executeUpdate();
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return result;
	}
	
	//關連線
	public void close() {
		try {
			connection.close();
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
