package bank;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TransferFeesCalculatorTest {
	
	@Parameters(name = "TC:{index}")
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {
			{true,  5000,  50000,  50000,  0.001f},
			{true,  5000,  50000,  150000, 0.0005f},
			{true,  5000,  150000, 50000,  0.005f},
			{true,  5000,  150000, 150000, 0.0025f},
			{true,  15000, 50000,  50000,  0.0005f},
			{true,  15000, 50000,  150000, 0.00025f},
			{true,  15000, 150000, 50000,  0.0025f},
			{true,  15000, 150000, 150000, 0.00125f},
			{false, 5000,  50000,  50000,  0.002f},
			{false, 5000,  50000,  150000, 0.001f},
			{false, 5000,  150000, 50000,  0.01f},
			{false, 5000,  150000, 150000, 0.005f},
			{false, 15000, 50000,  50000,  0.001f},
			{false, 15000, 50000,  150000, 0.0005f},
			{false, 15000, 150000, 50000,  0.005f},
			{false, 15000, 150000, 150000, 0.0025f},
		});
	}

	private boolean student;
	private int withdrawn;
	private int accountOut;
	private int accountIn;
	private float result;
	
	private static FeesCalculator tester;
	
	public TransferFeesCalculatorTest(boolean student, int amount, int fromAccountBalance, int toAccountBalance, float result) {
		this.student = student;
		this.withdrawn = amount;
		this.accountOut = fromAccountBalance;
		this.accountIn = toAccountBalance;
		this.result = result;
	}
	
	@BeforeClass
	public static void init() {
		tester = new FeesCalculator();
	}
	
	@Test
	public void testFeeCalculation() {
		assertEquals("Result", Math.round(this.withdrawn*this.result), tester.calculateTransferFee(this.withdrawn, this.accountOut, this.accountIn, this.student), 0.0001f);
	}

}
