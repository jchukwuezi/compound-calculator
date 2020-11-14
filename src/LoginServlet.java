import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	//SQL Code
	private final String CHECK_SQL = "SELECT * FROM Users WHERE " + "username =? AND password =?";
	//Page to Open after successful login
	private final String REDIRECT_PAGE = "calculate.html";
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//getting the details entered in the form
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		/*checking if the records exist in the database*/
		try {
				//connecting to the database
				Connection con;
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calcaccounts?serverTimezone=UTC", "root", "root");
					
				//prepared statement to search for user name and password
				PreparedStatement searchUser = con.prepareStatement(CHECK_SQL);
				
				//pass in parameters
				searchUser.setString(1, username);
				searchUser.setString(2, password);
				
				ResultSet rs = searchUser.executeQuery();
				
				//cursor iterating through rows
				if (rs.next()) { //if true, row exists. Redirect to CalculateServlet
					out.println("<html>");
					out.println("<body>");
					out.println("<p>Login Successful</p>");
					out.println("<a href= " + REDIRECT_PAGE + ">Calculate Page</a>");
					out.println("</body>");
					out.println("</html");
				}
				else {
					out.println("<html><body><p>Incorrect password or username</p></body></html>");
				}
				
				//close connection
				searchUser.close();
					
		} catch (Exception e) {
			out.println("<html><body><p>Error connecting to database</p></body></html>");
		}
			out.close();
		
		/*
		//forward to CalculateServlet
		RequestDispatcher rd = request.getRequestDispatcher("CalculationServlet");
		rd.forward(request, response);
		*/	
	}
	

}
