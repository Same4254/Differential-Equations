package MagicSquare;

public class MagicSquare {
	public static long square(long num) {
//		return num * num;
		return num;
	}
	
	public static boolean isSquare(long num) {
//		double temp = Math.sqrt(num);
//		return ((int) temp) == temp;
		return true;
	}
	
	/**
	 * This is a narrowed version that only uses 3 for loops. 
	 * This was derived from the system of equations that make up a magic square
	 */
	public static void narrowed() {
		int max = 100;
		
		for(long b = 0; b < max; b++) {
			System.out.println(b);
			long bs = square(b);
			
		for(long h = 0; h < max; h++) {
			long hs = square(h);
			
			if(bs == hs) continue;
			
			long n = (3 * bs) + (3 * hs);
			
			if(n % 2 != 0) continue;
			n /= 2;
			
			if(n % 3 != 0) continue;
			long es = n / 3;
			
			for(long g = 0; g < max; g++) {
				long gs = square(g);
				
				long as = es - bs + gs;
				if(as < 0) continue;
				if(!isSquare(as)) continue;
				
				if(gs == as || gs == hs || gs == bs || as == hs || as == bs) 
					continue;
				
				if(as + es != gs + hs || as + bs != gs + es) 
					continue;
				
				long fs = (2 * gs) - bs;
				
				if(fs < 0) 
					continue;
				
				if(fs == gs || fs == as || fs == hs || fs == bs)
					continue;
				
				if(as + gs != es + fs)
					continue;
				
				long cs = n - as - bs;
				long ds = n - as - gs;
				long is = n - gs - hs;
				
				if(cs < 0 || ds < 0 || is < 0)
					continue;
				
				if(!isSquare(cs) || !isSquare(ds) || !isSquare(is))
					continue;
				
				if(is == ds || is == cs || is == fs || is == gs || is == as || is == hs || is == bs ||
							 ds == cs || ds == fs || ds == gs || ds == as || ds == hs || ds == bs ||
							 cs == fs || cs == gs || cs == as || cs == hs || cs == bs)
					continue;
				
				if(ds + es + fs != n || gs + hs + is != n || as + ds + gs != n || bs + es + hs != n || cs + fs + is != n || cs + es + gs != n || as + es + is != n)
					continue;
				
				System.out.println("-------------");
				System.out.println("n = " + n);
				System.out.println(as + ", " + bs + ", " + cs + ", ");
				System.out.println(ds + ", " + es + ", " + fs + ", ");
				System.out.println(gs + ", " + hs + ", " + is + ", ");
			}
		}}
	}
	
	/**
	 * This is the tempting shotgun approach to this problem, where there is a for loop for every variable. 
	 * There are 9 for-loops, making this EXTREMELY slow. 
	 */
	public static void bad() {
		int max = 20;
		
		for(long a = 1; a < max; a++) {
		for(long b = 1; b < max; b++) {
		for(long c = 1; c < max; c++) {
		for(long d = 1; d < max; d++) {
		for(long e = 1; e < max; e++) {
		for(long f = 1; f < max; f++) {
		for(long g = 1; g < max; g++) {
		for(long h = 1; h < max; h++) {
		for(long i = 1; i < max; i++) {
			
			if(a == b || a == c || a == d || a == e || a == f || a == g || a == h || a == i ||
					     b == c || b == d || b == e || b == f || b == g || b == h || b == i ||
					     c == d || c == e || c == f || c == g || c == h || c == i ||
					     d == e || d == f || d == g || d == h || d == i || 
					     e == f || e == g || e == h || e == i ||
					     f == g || f == h || f == i ||
					     g == h || g == i ||
					     h == i)
				continue;
			
			long n = a + b + c;
			
			if(d + e + f != n || g + h + i != n || a + d + g != n || b + e + h != n || c + f + i != n || c + e + g != n || a + e + i != n)
				continue;
			
			System.out.println("-------------");
			System.out.println("n = " + n);
			System.out.println(a + ", " + b + ", " + c + ", ");
			System.out.println(d + ", " + e + ", " + f + ", ");
			System.out.println(g + ", " + h + ", " + i + ", ");
			
		}}}}}}}}}
	}
	
	public static void main(String[] args) {
		narrowed();
	}
}
