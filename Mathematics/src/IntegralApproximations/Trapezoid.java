package IntegralApproximations;

import java.util.function.Function;

public class Trapezoid {
	public static double approximate(Function<Double, Double> function, double a, double b, int n) {
		double dx = (b - a) / n;
		
		double sum = 0;
		double x = a;
		
		for(int i = 0; i <= n; i++) {
			if(i == 0 || i == n) 
				sum += function.apply(x);
			else 
				sum += 2.0 * function.apply(x);
			
			x += dx;
		}
		
		return (dx / 2.0) * sum;
	}
}
