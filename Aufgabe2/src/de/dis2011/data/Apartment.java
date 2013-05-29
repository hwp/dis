package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Apartment extends Estate {
	private int floor;
	private int rent;
	private int rooms;
	private boolean balcony;
	private boolean kitchen;

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public boolean hasBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean hasKitchen() {
		return kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	public static Apartment load(int id) {
		Apartment apt = new Apartment();

		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM Apartment WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				apt.setFloor(rs.getInt("Floor"));
				apt.setRent(rs.getInt("Rent"));
				apt.setRooms(rs.getInt("Rooms"));
				apt.setBalcony(rs.getBoolean("Balcony"));
				apt.setKitchen(rs.getBoolean("Kitchen"));

				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return apt;
	}

	@Override
	public void save(int managerId) {
		super.save(managerId);

		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			if (! Estate.isApartment(getId())) {
				String insertSQL = "INSERT INTO Apartment(ID, Floor, Rent, Rooms, Balcony, Kitchen) VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, getId());
				pstmt.setInt(2, getFloor());
				pstmt.setInt(3, getRent());
				pstmt.setInt(4, getRooms());
				pstmt.setBoolean(5, hasBalcony());
				pstmt.setBoolean(6, hasKitchen());
				pstmt.executeUpdate();
				
				

				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				String updateSQL = "UPDATE Apartment SET Floor = ?, Rent = ?, Rooms = ?, Balcony = ?, Kitchen = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				pstmt.setInt(1, getFloor());
				pstmt.setInt(2, getRent());
				pstmt.setInt(3, getRooms());
				pstmt.setBoolean(4, hasBalcony());
				pstmt.setBoolean(5, hasKitchen());
				pstmt.setInt(6, getId());
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
			String deleteSQL = "delete from Apartment WHERE id = ?";
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
