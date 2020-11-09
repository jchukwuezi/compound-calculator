import java.text.DecimalFormat;

public class TestCalculation {
	double baseAmount, interestRate, totalInterest, timeInYrs;	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	public TestCalculation() {
		baseAmount = 10000;
		interestRate = 5;
		timeInYrs = 3;
	
		System.out.println("Base amount : " + df.format(baseAmount));
		System.out.println("Interest rate : " + df.format(interestRate));
		System.out.println("Final Value : " +  df.format(calculateCompoundInterest()));
	}
	
	public double calculateCompoundInterest() {
		double compoundInterest;
		//calculating total interest
		totalInterest = 1 + (interestRate/100);
		
		//calculate final value to return
		compoundInterest = baseAmount * (Math.pow(totalInterest, timeInYrs));
		
		return compoundInterest;
	}
	
	
	
	public static void main(String[] args) {
		TestCalculation tc = new TestCalculation();
	}

}
