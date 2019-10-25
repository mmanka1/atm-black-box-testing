package bank;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class DepositFeesCalculatorTest {
    @Parameterized.Parameters(name = "TC:{index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 15000, 20000, true, 0.005f }, { 60000, 200000, false, 0.005f }, { 6000, 700000, true, 0.005f }, { 40000, 5000000, false, 0.005f }, { -10000, -10000, true, 0f }
        });
    }

    private int amount;
    private int accountBalance;
    private boolean isStudent;
    private float expectedInterest;
    private FeesCalculator feesCalculator;

    public DepositFeesCalculatorTest(int amount, int accountBalance, boolean isStudent, float expectedInterest) {
        this.amount = amount;
        this.accountBalance = accountBalance;
        this.isStudent = isStudent;
        this.expectedInterest = expectedInterest;
    }

    @Before
    public void setUp() {
        feesCalculator = new FeesCalculator();
    }

    @Test
    public void verifyInterest(){
        int interest = feesCalculator.calculateDepositInterest(amount, accountBalance, isStudent);
        assertEquals(Math.round(amount * expectedInterest), interest);
    }
}