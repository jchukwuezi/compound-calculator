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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterServlet extends HttpServlet {
	//SQL Code
	private final String UPDATE_TABLE_SQL = "INSERT into users "
			+ "(username, password)" + " VALUES(?, ?)";
	
	private String CHECK_SQL = "SELECT * FROM Users WHERE " + "username =?";
	
	//Response after successful registration
	private final String REDIRECT_PAGE = "login.html";
	
	//Response after unsuccessful login
	private final String REDO = "register.html";	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmedPassword = request.getParameter("confirmedPassword");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
			//form validation: checking to see if password == confirmed password
			if (password.equals(confirmedPassword)) {
				
			try {
					// initialize database
					Connection con;
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calcaccounts?serverTimezone=UTC", "root",
							"root");
	
					// Result set: to check if a username already exists
					ResultSet rs;
	
					// Prepared statement: used to add a user and their password to the database
					PreparedStatement checkUser = con.prepareStatement(CHECK_SQL);
					PreparedStatement createUser = con.prepareStatement(UPDATE_TABLE_SQL);
					
	
					// pass in values as parameters
					checkUser.setString(1, username);
					createUser.setString(1, username);
					createUser.setString(2, password);
	
	
					rs = checkUser.executeQuery();
					if (rs.next()) { // if true, the username exists already
						out.println("<html>");
						out.println("<body>");
						out.println("<p>This username already exists, please choose a unique one</p>");
						out.println("<a href= " + REDO + ">Register</a>");
						out.println("</body>");
						out.println("</html");
					}
					
					else { //if unique, new user will be created
						// executes update statement on prepared statement (DML)
						int updatedRows = createUser.executeUpdate();
						out.println("<html>");
						out.println("<body>");
						out.println("<p>");
						out.println("Thank you " + username + " for joining, your details are now stored in our database");
						out.println("<a href= " + REDIRECT_PAGE + ">Login</a>");
						out.println("</p>");
						out.println("</body>");
						out.println("</html>");		
					}
				
					// close connection
					createUser.close();
					checkUser.close();
					
			} catch (Exception e) {
				out.println("<html><body><p>Error with prepared statement or connecting to database</p></body></html>");
			}
			
			
		 }
			else {
				out.println("<html>");
				out.println("<body>");
				out.println("<p>");
				out.println("The two passwords do not match, please try again");
				out.println("<a href= " + REDO + ">Re-Enter Details</a>");
				out.println("</p>");
				out.println("</body>");
				out.println("</html>");	
			}
			
			out.close();
	
    }	
		
				
 }
