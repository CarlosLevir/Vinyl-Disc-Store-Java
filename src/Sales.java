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
	String vinyl;
	String client;
	String date;
	ArrayList clientsList;
	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	Connection connection;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVinyl() {
		return vinyl;
	}

	public void setVinyl(String vinyl) {
		this.vinyl = vinyl;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Connection getConnection() {
		try {
			Class.forVinyl("com.mysql.jdbc.Driver");
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
				client.setVinyl(rs.getString("vinyl"));
				client.setClient(rs.getString("client"));
				client.setDate(rs.getString("date"));
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
					.prepareStatement("insert into clients(vinyl,client,date) values(?,?,?)");
			stmt.setString(1, vinyl);
			stmt.setString(2, client);
			stmt.setString(3, date);
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
			client.setVinyl(rs.getString("vinyl"));
			client.setClient(rs.getString("client"));
			client.setDate(rs.getString("date"));
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
					.prepareStatement("update clients set vinyl=?,client=?,date=? where id=?");
			stmt.setString(1, client.getVinyl());
			stmt.setString(2, client.getClient());
			stmt.setString(3, client.getDate());
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