import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class CalculationServlet extends HttpServlet {
	private static DecimalFormat df = new DecimalFormat("0.00");
	private final String REDIRECT_PAGE = "calculate.html";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String baseAmount = request.getParameter("baseAmount");
		String annualInterest =  request.getParameter("annualInterest");
		String numberOfYears = request.getParameter("numberOfYears");
		
		//casting parameters to doubles
		double baseAmnt = Double.parseDouble(baseAmount);
		double yrlyInterest = Double.parseDouble(annualInterest);
		double numberOfYrs = Double.parseDouble(numberOfYears);
		
		double compoundInterest, totalInterest;	
		//calculating total interest
		totalInterest = 1 + (yrlyInterest/100);
		//calculate final value to return
		compoundInterest = baseAmnt * Math.pow(totalInterest, numberOfYrs);
	    //cast value to String to return
		String cmpndInterest = String.valueOf(df.format(compoundInterest));
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<body>");
		out.println("Initial Balance: " + baseAmount + "<br>");
		out.println("Total Interest : " + annualInterest + " %" + "<br>");
		out.println("Final Value : " + cmpndInterest + "<br>");
		out.println("<a href= " + REDIRECT_PAGE + ">New Calculation</a>");
		out.println("</body>");
		out.println("</html>");
		
		
		out.close();
		
	}
	
}
