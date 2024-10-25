package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		Statement st = null;
		try {
			conn = DB.getConnection();
			
			//aqui o programado vai decidir se depois de um erro vai continur rodondo 
			//programa
			conn.setAutoCommit(false);
			
			st = conn.createStatement();
			
			int row1 = st.executeUpdate("Update seller set BaseSalary = 2090 where DepartmentId = 1");
			
			int x = 1;
			
//			if(x < 2) {
//				throw new SQLException("Fake error");
//			}
			
			int row2 = st.executeUpdate("Update Seller set BaseSalary = 3090 where DepartmentId = 2");
			
			//aqui vai mostras que agora a execulcao do programa termino
			conn.commit();
			System.out.println("Rows 1: "+row1);
			System.out.println("Rows 2: "+row2);
			
		} catch (SQLException e) {
			
			//caso de erro no codigo ele vai desfazer o que foi acrecentado ou deltado 
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back ! Caused by: "+e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Erro Trying to rollback ! Caused By: "+e1.getMessage());
			}
		} finally {
			DB.closeConnection();
			DB.closeStatement(st);
		}

	}

}
