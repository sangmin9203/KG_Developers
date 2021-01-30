package com.kg.dvlpr.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DevelopersDBConn {
private Connection con; // 접속객체 선언 
private Statement stmt;
private ResultSet rs;
	
	//접속객체 호출시  getConnection()를 호출 
	public Connection getConnection(){ 
		return con;
	}
	
	public DevelopersDBConn() //생성자
			throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		   //드라이버 메모리로딩 선언 
		
		con=DriverManager.getConnection
	("jdbc:oracle:thin:@localhost:1521/XEPDB1", "hr","hr");
	}
	
	public ArrayList<String> getData() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT userid FROM member");
			
			while(rs.next()) {
				arr.add(rs.getString("userid"));
				System.out.println("----getData userId----");
				System.out.println(rs.getString("userid"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
}

