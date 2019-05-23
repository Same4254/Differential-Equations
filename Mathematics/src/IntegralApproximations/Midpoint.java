package IntegralApproximations;

import java.util.function.Function;

public class Midpoint {
	public static double approximate(Function<Double, Double> function, double a, double b, double dx) {
//		double sum = 0;
//		double lastX = a;
//		double x = a + dx;
//		
//		for(int i = 0; i < n; i++) {
//			sum += function.apply((x + lastX) / 2.0) * dx;
//			
//			lastX = x;
//			x += dx;
//		}
//		
//		return sum;
		
		double area = 0;
        for(double i = a + dx + dx; i < b - dx; i += dx) {
            double dFromA = i - a;
            
            System.out.println(area);
            
            area += (function.apply(a + dFromA) + function.apply(a + dFromA - dx)) * (dx / 2.0);
        }
        
        return area;
	}
}
