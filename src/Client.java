import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class Client {
	int id;
	String name;
	String email;
	String address;
	ArrayList clientsList;
	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	Connection connection;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "docker", "docker");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	public ArrayList clientsList() {
		try {
			clientsList = new ArrayList();
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from clients");
			while (rs.next()) {
				Client client = new Client();
				client.setId(rs.getInt("id"));
				client.setName(rs.getString("name"));
				client.setEmail(rs.getString("email"));
				client.setAddress(rs.getString("address"));
				clientsList.add(client);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return clientsList;
	}

	public String save() {
		int result = 0;
		try {
			connection = getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("insert into clients(name,email,address) values(?,?,?)");
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, address);
			result = stmt.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		if (result != 0)
			return "listClient.xhtml?faces-redirect=true";
		else
			return "createClient.xhtml?faces-redirect=true";
	}

	public String edit(int id) {
		Client client = null;
		System.out.println(id);
		try {
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from clients where id = " + (id));
			rs.next();
			client = new Client();
			client.setId(rs.getInt("id"));
			client.setName(rs.getString("name"));
			client.setEmail(rs.getString("email"));
			client.setAddress(rs.getString("address"));
			sessionMap.put("editClient", client);
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/editClient.xhtml?faces-redirect=true";
	}

	public String update(Client client) {
		try {
			connection = getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("update clients set name=?,email=?,address=? where id=?");
			stmt.setString(1, client.getName());
			stmt.setString(2, client.getEmail());
			stmt.setString(3, client.getAddress());
			stmt.setInt(4, client.getId());
			stmt.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println();
		}
		return "/listClient.xhtml?faces-redirect=true";
	}

	public void delete(int id) {
		try {
			connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement("delete from clients where id = " + id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}