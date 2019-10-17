package org.oar.chainApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecondServlet extends HttpServlet{
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	 
    	 //FETCH REQUEST OBJECT BACK FROM THE SCOPE OF THE APPLICATION//
    	 String pname=(String) req.getAttribute("prdnm");    /*NOTE:=Here the getAttribute returns an Object and it has to be casted into a String type
    	                                                                  since the values are of String type*/
    	 String  pqty=(String) req.getAttribute("prdqty");            
    	 int qty=Integer.parseInt(pqty);                     /*NOTE:=Here the pqty should be of string type and pqty is of string type so parseInt is used*/
          double price=40000.00;
          double totalsum=(price*qty);                             //BUSINESS LOGIC//
          
          //THEN THE RESPONSE HAS TO SHOWN ON ANOTHER HTML PAGE//
          PrintWriter out=resp.getWriter();
          out.println("<html><body bgcolor='yellow'>"
          		+ "<h1>Product Details Are"+pname+" "+totalsum+"</h1>"
          				+ "</body></html>");        
          out.flush();
          out.close();                                          //PRESENTATION LOGIC//
          
          Connection con=null;
          PreparedStatement pstmt=null;
          String qry="insert into oar.product values(?,?,?)";
          try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Class Loaded and Registered");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=dinga");
			 System.out.println("Connection Established with the DBServer");
			 pstmt=con.prepareStatement(qry);
			 System.out.println("Statement or Platform Created");
			 
			 //SET THE VALUES FOR PLACEHOLDER BEFORE THE EXECUTION//
			 pstmt.setString(1,pname);
			 pstmt.setInt(2, qty);
			 pstmt.setDouble(3,totalsum);
			 
			 pstmt.executeUpdate();
			 System.out.println("All the Sql queries are executed");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
        finally {
        	if(pstmt!=null) {
        	try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	}
        	if(con!=null) {
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	}
        }
		
     }
}
