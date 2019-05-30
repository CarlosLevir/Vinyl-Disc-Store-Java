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
public class Vinyl {
	int id;
	String name;
	String price;
	String singer;
	ArrayList vinylsList;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
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

	public ArrayList vinylsList() {
		try {
			vinylsList = new ArrayList();
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from vinyls");
			while (rs.next()) {
				Vinyl vinyl = new Vinyl();
				vinyl.setId(rs.getInt("id"));
				vinyl.setName(rs.getString("name"));
				vinyl.setPrice(rs.getString("price"));
				vinyl.setSinger(rs.getString("singer"));
				vinylsList.add(vinyl);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return vinylsList;
	}

	public String save() {
		int result = 0;
		try {
			connection = getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("insert into vinyls(name,price,singer) values(?,?,?)");
			stmt.setString(1, name);
			stmt.setString(2, price);
			stmt.setString(3, singer);
			result = stmt.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		if (result != 0)
			return "listVinyl.xhtml?faces-redirect=true";
		else
			return "createVinyl.xhtml?faces-redirect=true";
	}

	public String edit(int id) {
		Vinyl vinyl = null;
		System.out.println(id);
		try {
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from vinyls where id = " + (id));
			rs.next();
			vinyl = new Vinyl();
			vinyl.setId(rs.getInt("id"));
			vinyl.setName(rs.getString("name"));
			vinyl.setPrice(rs.getString("price"));
			vinyl.setSinger(rs.getString("singer"));
			sessionMap.put("editVinyl", vinyl);
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/editVinyl.xhtml?faces-redirect=true";
	}

	public String update(Vinyl vinyl) {
		try {
			connection = getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("update vinyls set name=?,price=?,singer=? where id=?");
			stmt.setString(1, vinyl.getName());
			stmt.setString(2, vinyl.getPrice());
			stmt.setString(3, vinyl.getSinger());
			stmt.setInt(4, vinyl.getId());
			stmt.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println();
		}
		return "/listVinyl.xhtml?faces-redirect=true";
	}

	public void delete(int id) {
		try {
			connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement("delete from vinyls where id = " + id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}