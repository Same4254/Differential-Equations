package Graph;

import java.awt.geom.Path2D;
import java.util.function.Function;

public class GraphFunction {
	private Function<Double, Double> function;
	private Path2D.Double shape;
	
	public GraphFunction(Function<Double, Double> function) {
		this.function = function;
		this.shape = new Path2D.Double();
	}
	
	public Function<Double, Double> getFunction() { return function; }
	public Path2D.Double getShape() { return shape; }
}
