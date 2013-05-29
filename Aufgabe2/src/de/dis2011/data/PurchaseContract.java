package de.dis2011.data;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PurchaseContract extends Contract {
	private int noi;
	private double interestRate;
	private int purchaser;
	private int houseID;

	public int getNoi() {
		return noi;
	}

	public void setNoi(int noi) {
		this.noi = noi;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(int purchaser) {
		this.purchaser = purchaser;
	}

	public int getHouseID() {
		return houseID;
	}

	public void setHouseID(int houseID) {
		this.houseID = houseID;
	}

	@Override
	public void save() {
		super.save();

		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			String insertSQL = "INSERT INTO PurchaseContract(ContractNo, NoOfInstallments, InterestRate, Purchaser, HouseID) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, getContractNo());
			pstmt.setInt(2, getNoi());
			pstmt.setDouble(3, getInterestRate());
			pstmt.setInt(4, getPurchaser());
			pstmt.setInt(5, getHouseID());
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static PurchaseContract load(int id) {
		PurchaseContract ret = null;

		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM Contract, PurchaseContract WHERE Contract.ContractNo = ? and Contract.ContractNo = PurchaseContract.ContractNo";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = new PurchaseContract();
				ret.setContractNo(id);
				ret.setDate(rs.getDate("CDate"));
				ret.setPlace(rs.getString("Place"));
				ret.setNoi(rs.getInt("NoOfInstallments"));
				ret.setInterestRate(rs.getDouble("InterestRate"));
				ret.setPurchaser(rs.getInt("Purchaser"));
				ret.setHouseID(rs.getInt("HouseID"));

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
		out.println("Purchase Contract (" + getContractNo() + ")");
		out.println("\tDate : " + getDate());
		out.println("\tPlace : " + getPlace());
		out.println("\tNo. of Intallments : " + getNoi());
		out.println("\tInterest Rate : " + getInterestRate());
		out.println("\tPurchaser ID : " + getPurchaser());
		out.println("\tHouse ID : " + getHouseID());
	}
}
