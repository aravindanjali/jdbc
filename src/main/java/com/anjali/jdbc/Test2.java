package com.anjali.jdbc;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test2 {
	
	
	public static void main(String[] args) throws IOException, ParseException {
		 Connection con = null;
			Statement st = null;
			ResultSet rs=null;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbe1","root","Newpassword");
				st = con.createStatement();
			    rs = st.executeQuery("select * from employee where designation='Jr Programmer' and department = 'computer'");
			    while(rs.next())
	            {
	                System.out.println(rs.getString(1));    //First Column
	                System.out.println(rs.getString(2));    //Second Column
	                System.out.println(rs.getString(3));    //Third Column
	                System.out.println(rs.getString(4));    //Fourth Column
	                System.out.println(rs.getString(5));    //Fifth Column
	            }
			} 
	        catch (Exception e) {
	            e.printStackTrace();
	        }
			finally {
	            try {
	                st.close();
	                con.close();
	            } 
	            catch (Exception e) {
	                e.printStackTrace();
			   }
		     }
		
		JSONParser jsonparser = new JSONParser();
		try {
			FileReader reader = new FileReader(".\\jsonfiles\\employee.json");
			Object obj = jsonparser.parse(reader);
			JSONObject empjsonobj = (JSONObject)obj;
			JSONArray jsonArray = (JSONArray) empjsonobj.get("employee-details");
		
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbe1","root","Newpassword");
			st = con.createStatement();
			
		PreparedStatement pstmt = con.prepareStatement("insert into employee values (?, ?, ?, ?, ? )");
		for(Object object : jsonArray) {
			JSONObject record = (JSONObject) object;
		
		int eno= Integer.parseInt((String) record.get("empno"));
		String ename= (String) record.get("empname");
		int sal = Integer.parseInt((String) record.get("salary"));
		String desg= (String) record.get("designation");
		String dpmt= (String) record.get("department");
		
		System.out.println("Employee Number:"+ eno);
		System.out.println("Employee Name:"+ ename);
		System.out.println("Employee Salary:"+ sal);
		System.out.println("Employee Designation:"+ desg);
		System.out.println("Employee Department:"+ dpmt);
		
		pstmt.setInt(1,eno);
        pstmt.setString(2, ename);
        pstmt.setInt(3, sal);
        pstmt.setString(4,desg );
        pstmt.setString(5,dpmt);
        pstmt.executeUpdate();
		}  
        System.out.println("Records inserted.....");
     } 
		catch (FileNotFoundException e) {
        e.printStackTrace();
        } 
		catch (IOException e) {
        e.printStackTrace();
        } 
		catch (ParseException e) {
        e.printStackTrace();
        } 
		catch (Exception e) {
        e.printStackTrace();
         }
		
	}

	
	
		
		

}

