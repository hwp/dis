package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2011.data.DB2ConnectionManager;

/**
 *
 */
public abstract class Estate {
	private int id = -1;
	private String city;
	private int postalCode;
	private String street;
	private int streetNo;
	private int area;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(int streetNo) {
		this.streetNo = streetNo;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public static Estate load(int id) {
		Estate est = null;
		if (isApartment(id)) {
			est = Apartment.load(id);
			loadEstate(est, id);
		} else if (isHouse(id)) {
			est = House.load(id);
			loadEstate(est, id);
		}
		return est;
	}

	protected static boolean isApartment(int id) {
		boolean r = false;

		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM Apartment WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			r = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	protected static boolean isHouse(int id) {
		boolean r = false;

		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM House WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			r = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;
	}

	/**
	 *
	 */
	protected static void loadEstate(Estate ts, int id) {
		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM Estate WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ts.setId(id);
				ts.setCity(rs.getString("City"));
				ts.setStreet(rs.getString("Street"));
				ts.setStreetNo(rs.getInt("StreetNumber"));
				ts.setPostalCode(rs.getInt("PostalCode"));
				ts.setArea(rs.getInt("SquareArea"));

				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	protected void save(int managerId) {
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			if (getId() == -1) {
				String insertSQL = "INSERT INTO Estate(City, Street, StreetNumber, PostalCode, SquareArea, Manager) VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, getCity());
				pstmt.setString(2, getStreet());
				pstmt.setInt(3, getStreetNo());
				pstmt.setInt(4, getPostalCode());
				pstmt.setInt(5, getArea());
				pstmt.setInt(6, managerId);
				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				String updateSQL = "UPDATE Estate SET City = ?, Street = ?, StreetNumber = ?, PostalCode = ?, SquareArea = ?, Manager = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				pstmt.setString(1, getCity());
				pstmt.setString(2, getStreet());
				pstmt.setInt(3, getStreetNo());
				pstmt.setInt(4, getPostalCode());
				pstmt.setInt(5, getArea());
				pstmt.setInt(6, managerId);
				pstmt.setInt(7, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		try {
			String deleteSQL = "delete from Estate WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(deleteSQL);

			pstmt.setInt(1, getId());
			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
