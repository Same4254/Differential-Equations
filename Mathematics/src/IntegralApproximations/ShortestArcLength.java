package IntegralApproximations;

import java.util.function.Function;

public class ShortestArcLength {
	public static void main(String[] args) {
		double dx = 0.000001;
		
		double smallA = 0, smallB = 0, smallLength = 5;
		
//		for(double b = .01; b < 1.0; b += dx) {
			final double tempB = 0.153764; 
			
			Function<Double, Double> tempFunction = x -> {
				return Math.pow(Math.sin(Math.PI * x), tempB);
			};
			
			double tempArea = Midpoint.approximate(tempFunction, 0, 1, dx);
			
			System.out.println(tempArea);
			
			final double tempA = 1.0 / tempArea;
			
			Function<Double, Double> function = x -> {
				return tempA * Math.pow(Math.sin(Math.PI * x), tempB);
			};
			
			Function<Double, Double> derivative = x -> {
				return Math.PI * tempA * tempB * Math.cos(Math.PI * x) * Math.pow(Math.sin(Math.PI * x), tempB - 1.0);
			};
			
			Function<Double, Double> arcFunction = x -> {
				return Math.sqrt(1.0 + Math.pow(derivative.apply(x), 2));
			};
			
//			double area = Midpoint.approximate(function, 0, 1, dx);
			
			System.out.println("---a-df-a-d-f-asd-f-a-df-");
			double arcLength = Midpoint.approximate(arcFunction, 0, 1, dx);

//			if(Math.abs(1.0 - area) < 0.001 && arcLength > 2.5) {
//				if(arcLength < smallLength) {
//					smallA = tempA;
//					smallB = tempB;
//					smallLength = arcLength;
//				}
				
				System.out.println("----");
				System.out.println("A: " + tempA);
				System.out.println("B: " + tempB);
//				System.out.println("Area: " + area);
				System.out.println("Length: " + arcLength);
//			}
//		}
		
//		System.out.println("---");
//		System.out.println("Smallest: ");
//		System.out.println("A: " + smallA);
//		System.out.println("B: " + smallB);
	}
}
