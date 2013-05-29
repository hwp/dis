package de.dis2011.data;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TenancyContract extends Contract {
	private Date startDate;
	private String duration;
	private int addtionalCosts;
	private int tenant;
	private int aptID;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getAddtionalCosts() {
		return addtionalCosts;
	}

	public void setAddtionalCosts(int addtionalCosts) {
		this.addtionalCosts = addtionalCosts;
	}

	public int getTenant() {
		return tenant;
	}

	public void setTenant(int tenant) {
		this.tenant = tenant;
	}

	public int getAptID() {
		return aptID;
	}

	public void setAptID(int aptID) {
		this.aptID = aptID;
	}

	@Override
	public void save() {
		super.save();

		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			String insertSQL = "INSERT INTO TenancyContract(ContractNo, StartDate, Duration, AdditionalCosts, Tenant, AptID) VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, getContractNo());
			pstmt.setDate(2, getStartDate());
			pstmt.setString(3, getDuration());
			pstmt.setInt(4, getAddtionalCosts());
			pstmt.setInt(5, getTenant());
			pstmt.setInt(6, getAptID());
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static TenancyContract load(int id) {
		TenancyContract ret = null;

		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM Contract, TenancyContract WHERE Contract.ContractNo = ? and Contract.ContractNo = TenancyContract.ContractNo";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = new TenancyContract();
				ret.setContractNo(id);
				ret.setDate(rs.getDate("CDate"));
				ret.setPlace(rs.getString("Place"));
				ret.setStartDate(rs.getDate("StartDate"));
				ret.setDuration(rs.getString("Duration"));
				ret.setAddtionalCosts(rs.getInt("AdditionalCosts"));
				ret.setTenant(rs.getInt("Tenant"));
				ret.setAptID(rs.getInt("AptID"));

				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public void printInfo(PrintStream out) {
		out.println("Tenant Contract (" + getContractNo() + ")");
		out.println("\tDate : " + getDate());
		out.println("\tPlace : " + getPlace());
		out.println("\tStart Date : " + getStartDate());
		out.println("\tDuration : " + getDuration());
		out.println("\tAdditional Costs : " + getAddtionalCosts());
		out.println("\tTenant ID : " + getTenant());
		out.println("\tApartment ID : " + getAptID());
	}
}
