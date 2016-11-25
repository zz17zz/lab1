package demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class expreTest extends Polynomial {

	@Test
	public void testExpression() {
		Polynomial testp = new Polynomial();
		String mn="3*x+4*y";
		testp.expression(mn);
	}

}
