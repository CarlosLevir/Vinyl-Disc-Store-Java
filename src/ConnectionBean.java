import java.sql.Connection;
import java.sql.DriverManager;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ConnectionBean {
	static Connection connection;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "docker", "docker");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
}