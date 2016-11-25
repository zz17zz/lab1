package demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class test {

	@Test
	public void testSimplify() {
		Polynomial testp = new Polynomial();
		String mn="2*x+3*x*x*y";
		String str = "!simplify x=1y=2\r";
		testp.simplify(str,mn);
	}
}
