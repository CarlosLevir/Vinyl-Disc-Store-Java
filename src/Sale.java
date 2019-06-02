import java.sql.Connection;
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
public class Sale {
	int id;
	String vinyl;
	int price;
	String client;
	String date;
	ArrayList<Sale> salesList;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Connection getConnection() {
		return ConnectionBean.getConnection();
	}

	public ArrayList<Sale> salesList() {
		try {
			salesList = new ArrayList<Sale>();
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from sales order by id desc ");
			while (rs.next()) {
				Sale sale = new Sale();
				sale.setId(rs.getInt("id"));
				sale.setVinyl(rs.getString("vinyl"));
				sale.setClient(rs.getString("client"));
				sale.setPrice(rs.getInt("price"));
				sale.setDate(rs.getString("date"));
				salesList.add(sale);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return salesList;
	}

	public void save() {
		try {
			connection = getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("insert into sales(vinyl,client,price,date) values(?,?,?,?)");
			stmt.setString(1, vinyl);
			stmt.setString(2, client);
			stmt.setInt(3, price);
			stmt.setString(4, date);
			stmt.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String edit(int id) {
		Sale sale = null;
		try {
			connection = getConnection();
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from sales where id = " + (id));
			rs.next();
			sale = new Sale();
			sale.setId(rs.getInt("id"));
			sale.setVinyl(rs.getString("vinyl"));
			sale.setClient(rs.getString("client"));
			sale.setPrice(rs.getInt("price"));
			sale.setDate(rs.getString("date"));
			sessionMap.put("editSale", sale);
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/editSale.xhtml?faces-redirect=true";
	}

	public String update(Sale sale) {
		try {
			connection = getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("update sales set vinyl=?,client=?,price=? where id=?");
			stmt.setString(1, sale.getVinyl());
			stmt.setString(2, sale.getClient());
			stmt.setInt(3, sale.getPrice());
			stmt.setInt(4, sale.getId());
			stmt.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println();
		}
		return "/listSale.xhtml?faces-redirect=true";
	}

	public void delete(int id) {
		try {
			connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement("delete from sales where id = " + id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}