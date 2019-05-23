package IntegralApproximations;

import java.util.function.Function;

public class Simpson {
	public static double approximate(Function<Double, Double> function, double a, double b, int n) {
		double dx = (b - a) / n;
		
		double sum = 0;
		double x = a;
		
		for(int i = 0; i <= n; i++) {
			if(i == 0 || i == n) 
				sum += function.apply(x);
			else if(i % 2 == 1) 
				sum += 4.0 * function.apply(x);
			else
				sum += 2.0 * function.apply(x);
			
			x += dx;
		}
		
		return (dx / 3.0) * sum;
	}
}
