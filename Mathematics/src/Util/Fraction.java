package Util;

public class Fraction {
	private int num, den;
	
	public static Fraction toFraction(double number) {
		String string = String.valueOf(number);
		int digitsDecimal = string.length() - 1 - string.indexOf('.');
		int den = 1;
		
		for(int i = 0; i < digitsDecimal; i++) {
			number *= 10;
			den *= 10;
		}
		
		int num = (int) Math.round(number);
		int gcd = gcd(num, den);
		
		return new Fraction(num / gcd, den / gcd).reduce();
	}
	
//	public static double toDecimal(Fraction fraction) {
//		
//	}
	
	public static int gcd(int num, int den) {
		if(num % den == 0)
			return den;
		return gcd(den, num % den);
	}
	
	public Fraction(int num, int den) {
		this.num = num;
		this.den = den;
	}
	
	public Fraction(Fraction fraction) {
		this.num = fraction.num;
		this.den = fraction.den;
	}
	
	public Fraction(double number) { this(toFraction(number)); }
	
	public Fraction reduce() {
		int gcd = gcd(num, den);
		num /= gcd;
		den /= gcd;
		
		return this;
	}
	
	public Fraction add(Fraction other) {
		int newNum = (num * other.den) + (other.num * den);
		int newDen = den * other.den;
		
		return new Fraction(newNum, newDen).reduce();
	}
	
	public Fraction subtract(Fraction other) {
		int newNum = (num * other.den) - (other.num * den);
		int newDen = den * other.den;
		
		return new Fraction(newNum, newDen).reduce();
	}
	
	public Fraction multiply(Fraction other) {
		int newNum = num * other.num;
		int newDen = den * other.den;
		
		return new Fraction(newNum, newDen).reduce();
	}
	
	public Fraction divide(Fraction other) {
		int newNum = num * other.den;
		int newDen = den * other.num;
		
		return new Fraction(newNum, newDen).reduce();
	}
	
	public String toString() { return num + " / " + den; }

	public int getNum() { return num; }
	public int getDen() { return den; }
}
