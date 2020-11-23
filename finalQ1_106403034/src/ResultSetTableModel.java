import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel extends AbstractTableModel {
	private static final String URL = "jdbc:mysql://localhost/member?serverTimezone=UTC";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";
	
	private final Connection conn;
	private boolean connectedToDB = false;
	private final Statement statement;
	
	private ResultSet rs;
	private ResultSetMetaData metaData;
	
	private int rowNum;
	
	//constructor
	public ResultSetTableModel(String query) throws SQLException {
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		connectedToDB = true;
		statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		setQuery(query);
	}
	
	public Class getColumnClass(int col) throws IllegalStateException{
		if(!connectedToDB) {
    		throw new IllegalStateException("No connected to Database");
    	}
		try{
			String className = metaData.getColumnClassName(col+1);
			return Class.forName(className);
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		return Object.class;
	}
	
	public int getColumnCount() throws IllegalStateException {
    	if(!connectedToDB) {
    		throw new IllegalStateException("No connected to Database");
    	}
    	try {
    		return metaData.getColumnCount();
    	}
    	catch(SQLException sqlException) {
    		sqlException.printStackTrace();
    	}
    	return 0;
    }
	
	//
	public String getColumnName(int col) throws IllegalStateException {
		if(!connectedToDB) {
    		throw new IllegalStateException("No connected to Database");
    	}
		try {
			System.out.printf("%s\n", metaData.getColumnName(col+1));
			return metaData.getColumnName(col+1);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return "";
	}
    
    public int getRowCount() throws IllegalStateException {
		if(!connectedToDB) {
    		throw new IllegalStateException("No connected to Database");
    	}
		return rowNum;
	}
    
    public Object getValueAt(int row, int col) throws IllegalStateException{
    	if(!connectedToDB) {
    		throw new IllegalStateException("No connected to Database");
    	}
    	try {
    		rs.absolute(row+1);
    		return rs.getObject(col+1);
    	}
    	catch(SQLException sqlException) {
    		sqlException.printStackTrace();
    	}
    	return "";
    }
    
    //
    public void setQuery(String query) throws SQLException, IllegalStateException {
    	if(!connectedToDB) {
    		throw new IllegalStateException("No connected to Database");
    	}
		rs = statement.executeQuery(query);
		metaData = rs.getMetaData();
		rs.last();
		rowNum = rs.getRow();
		
		fireTableStructureChanged();
    }

    //
    public void disconnectFromDB() {
    	if(connectedToDB) {
    		try {
    			rs.close();
    			statement.close();
    			conn.close();
    		}
    		catch(SQLException sqlException) {
    			sqlException.printStackTrace();
    		}
    		finally {
    			connectedToDB = false;
    		}
    	}
    }
}
