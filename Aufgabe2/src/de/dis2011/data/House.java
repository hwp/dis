package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class House extends Estate {
	private int floors;
	private int price;
	private boolean garden;

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean hasGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	public static House load(int id) {
		House hs = new House();

		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM House WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				hs.setFloors(rs.getInt("Floors"));
				hs.setPrice(rs.getInt("Price"));
				hs.setGarden(rs.getBoolean("Garden"));

				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hs;
	}

	@Override
	public void save(int managerId) {
		super.save(managerId);

		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			if (!Estate.isHouse(getId())) {
				String insertSQL = "INSERT INTO House(ID, Floors, Price, Garden) VALUES (?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, getId());
				pstmt.setInt(2, getFloors());
				pstmt.setInt(3, getPrice());
				pstmt.setBoolean(4, hasGarden());
				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				String updateSQL = "UPDATE Apartment SET Floors = ?, Price = ?, Garden = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				pstmt.setInt(1, getFloors());
				pstmt.setInt(2, getPrice());
				pstmt.setBoolean(3, hasGarden());
				pstmt.setInt(4, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		try {
			String deleteSQL = "delete from House WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(deleteSQL);

			pstmt.setInt(1, getId());
			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		super.delete();
	}
}
