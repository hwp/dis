package de.dis2011.data;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public abstract class Contract {
	private int id = -1;
	private Date date;
	private String place;

	public int getContractNo() {
		return id;
	}

	public void setContractNo(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	protected void setDate(Date date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	protected void save() {
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			String insertSQL = "INSERT INTO Contract(CDate, Place) VALUES (?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setDate(1, new java.sql.Date(Calendar.getInstance().getTime()
					.getTime()));
			pstmt.setString(2, getPlace());
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				setContractNo(rs.getInt(1));
			}

			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void showAll(PrintStream out) {
		List<Integer> ids = new LinkedList<Integer>();

		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select ContractNo from Contract");
			while (rs.next()) {
				ids.add(rs.getInt("ContractNo"));
			}

			rs.close();
			stmt.close();

			for (int id : ids) {
				load(id).printInfo(out);
				out.println("******************");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Contract load(int id) {
		Contract ret = null;

		ret = PurchaseContract.load(id);
		if (ret == null) {
			ret = TenancyContract.load(id);
		}

		return ret;
	}

	public abstract void printInfo(PrintStream out);
}
