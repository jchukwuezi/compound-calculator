/*
 * Register Servlet = this class will be used to take in details of new users and write them into the database
 * 
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmedPassword = request.getParameter("confirmedPassword");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//form validation: checking to see if password == confirmed password
		if (password.equals(confirmedPassword)) {
			
		try {
					//initialize database
					Connection con;
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calcaccounts?serverTimezone=UTC", "root", "root");
					
					//Prepared statement: used to add a user and their password to the database
					PreparedStatement createUser = con.prepareStatement("INSERT into user "
					+ "(username, password)" + " VALUES(?, ?)");
					
					//pass in values as parameters
					createUser.setString(1, username);
					createUser.setString(2, password);
					
					//executes update statement on prepared statement (DML)
					int rowsUpdated = createUser.executeUpdate();
					
					//close connection
					createUser.close();
				
			} catch (Exception e) {
					out.println("<html>");
					out.println("<body>");
					out.println("<p>");
					out.println("Error with trying to write details to database");
					out.println("</p>");
					out.println("</body>");
					out.println("</html>");
			}
			
			out.println("<html>");
			out.println("<body>");
			out.println("<p>");
			out.println("Thank you " + username + " for joining, your details are now stored in our database");
			out.println("</p>");
			out.println("</body>");
			out.println("</html>");
		}
		
		else {
				out.println("<html>");
				out.println("<body>");
				out.println("<p>");
				out.println("Passwords do not match try again");
				out.println("</p>");
				out.println("</body>");
				out.println("</html>");
		}	
		
		
		try {
				//initialize database
				Connection con;
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calcaccounts?serverTimezone=UTC", "root", "root");
				
				//Prepared statement: used to add a user and their password to the database
				PreparedStatement createUser = con.prepareStatement("INSERT into user "
				+ "(username, password)" + " VALUES(?, ?)");
				
				try {
					//pass in values as parameters
					createUser.setString(1, username);
					createUser.setString(2, password);
				} catch (Exception e) {
					out.println("<html><body><p>Error passing in values as parameters</p></body></html>");
				}
				
				try {
					//executes update statement on prepared statement (DML)
					int rowsUpdated = createUser.executeUpdate();
				} catch (Exception e) {
					out.println("<html><body><p>Error executing update on prepared statment</p></body></html>");
				}
				
				try {
					//close connection
					createUser.close();
				} catch (Exception e) {
					out.println("<html><body><p>Error closing the prepared statement</p></body></html>");
				}
			
		} catch (Exception e) {
			out.println("<html><body><p>Eror with prepared statement or connecting to database</p></body></html>");
		}
		
		out.println("<html>");
		out.println("<body>");
		out.println("<p>");
		out.println("Thank you " + username + " for joining, your details are now stored in our database");
		out.println("</p>");
		out.println("</body>");
		out.println("</html>");
		
	}
	
	
		/*
		//basic SQL to view all users in the database
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from user");
		*/			
}
