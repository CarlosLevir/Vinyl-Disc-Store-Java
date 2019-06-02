import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class Vinyl {
	int id;
	String name;
	int price;
	String singer;
	String client;
	ArrayList<Vinyl> vinylsList;
	
	DateTimeFormatter formatter =
		    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
		                     .withLocale(Locale.US)
		                     .withZone(ZoneId.systemDefault());

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}
	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public String getNow() {
		return formatter.format(Instant.now());
	}

	public Connection getConnection() {
		return ConnectionBean.getConnection();
	}

	public ArrayList<Vinyl> vinylsList() {
		try {
			vinylsList = new ArrayList<Vinyl>();
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from vinyls");
			while (rs.next()) {
				Vinyl vinyl = new Vinyl();
				vinyl.setId(rs.getInt("id"));
				vinyl.setName(rs.getString("name"));
				vinyl.setPrice(rs.getInt("price"));
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
			stmt.setInt(2, price);
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
		try {
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from vinyls where id = " + (id));
			rs.next();
			vinyl = new Vinyl();
			vinyl.setId(rs.getInt("id"));
			vinyl.setName(rs.getString("name"));
			vinyl.setPrice(rs.getInt("price"));
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
			PreparedStatement stmt = connection.prepareStatement("update vinyls set name=?,price=?,singer=? where id=?");
			stmt.setString(1, vinyl.getName());
			stmt.setInt(2, vinyl.getPrice());
			stmt.setString(3, vinyl.getSinger());
			stmt.setInt(4, vinyl.getId());
			stmt.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println();
		}
		return "/listVinyl.xhtml?faces-redirect=true";
	}
	
	public String editSell(int id) {
		Vinyl vinyl = null;
		try {
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from vinyls where id = " + (id));
			rs.next();
			vinyl = new Vinyl();
			vinyl.setId(rs.getInt("id"));
			vinyl.setName(rs.getString("name"));
			vinyl.setPrice(rs.getInt("price"));
			vinyl.setSinger(rs.getString("singer"));
			sessionMap.put("editSell", vinyl);
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/createSale.xhtml?faces-redirect=true";
	}
	
	public String sell(Vinyl vinyl) {
		try {
			Sale sale = new Sale();
			sale.setVinyl(vinyl.getName());
			sale.setClient(vinyl.getClient());
			sale.setPrice(vinyl.getPrice());
			sale.setDate(vinyl.getNow());
			sale.save();

			connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement("delete from vinyls where id = " + vinyl.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/listSale.xhtml?faces-redirect=true";
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