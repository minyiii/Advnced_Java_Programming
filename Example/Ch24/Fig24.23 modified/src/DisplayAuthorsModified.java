import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DisplayAuthorsModified {
	
	public static void main(String[] args) {
		final String DATABASE_URL = "jdbc:mysql://localhost/books?serverTimezone=UTC";
		String SELECT_QUERY = "SELECT authorID, firstName, lastName FROM authors";
		
		try {
			Connection connection = DriverManager.getConnection(DATABASE_URL, "java", "java");
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
			
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			System.out.println("Authors Table of books Database:\n");
			
			for(int i=1;i<=numberOfColumns;i++) {
				System.out.printf("%-10s\t", metaData.getColumnName(i));
			}
			System.out.println();
			
			while(resultSet.next()) {
				for(int i=1;i<=numberOfColumns;i++) {
					System.out.printf("%-10s\t", resultSet.getObject(i));
				}
				System.out.println();
			}
			
			//------------- 插入一新的row -------------//
			/*resultSet.moveToInsertRow();
			resultSet.updateInt(1, 6);
			resultSet.updateString(2, "Shi-Jen");
			resultSet.updateNString(3, "Lin");
			resultSet.insertRow();
			resultSet.beforeFirst();
			
			System.out.println();
			//改完後的確認，法一：直接用resultSet去看有沒有改
			System.out.println("After inserting a new record (in resultSet)\n");
			metaData = resultSet.getMetaData();
			numberOfColumns = metaData.getColumnCount();
			for(int i=1;i<=numberOfColumns;i++) {
				System.out.printf("%-10s\t", metaData.getColumnName(i));
			}
			System.out.println();
			while(resultSet.next()) {	//當還有下一筆(下一個row)
				for(int i=1;i<=numberOfColumns;i++) {	//去抓該row的第i個物件
					System.out.printf("%-10s\t", resultSet.getObject(i));
				}
				System.out.println();
			}
			System.out.println();
			//改完後的確認，法二：重下select語法
			resultSet = statement.executeQuery(SELECT_QUERY);
			metaData = resultSet.getMetaData();
			numberOfColumns = metaData.getColumnCount();
			System.out.println("After inserting a new record (in database)\n");
			for(int i=1;i<=numberOfColumns;i++) {
				System.out.printf("%-10s\t", metaData.getColumnName(i));
			}
			System.out.println();
			while(resultSet.next()) {	//當還有下一筆(下一個row)
				for(int i=1;i<=numberOfColumns;i++) {	//去抓該row的第i個物件
					System.out.printf("%-10s\t", resultSet.getObject(i));
				}
				System.out.println();
			}*/
			
			//------------- 刪除row -------------//
			statement.executeUpdate("DELETE FROM authors WHERE lastName = 'Lin'");
			System.out.println();
			resultSet = statement.executeQuery(SELECT_QUERY);
			metaData = resultSet.getMetaData();
			numberOfColumns = metaData.getColumnCount();
			System.out.println("After deleting the last record:\n");
			for(int i=1;i<=numberOfColumns;i++) {
				System.out.printf("%-10s\t", metaData.getColumnName(i));
			}
			System.out.println();
			while(resultSet.next()) {
				for(int i=1;i<=numberOfColumns;i++) {
					System.out.printf("%-10s\t", resultSet.getObject(i));
				}
				System.out.println();
			}
		}	// try end
		
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}
