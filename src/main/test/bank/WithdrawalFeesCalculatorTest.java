package bank;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class WithdrawalFeesCalculatorTest {
    private FeesCalculator withdrawalFee;
    private static int amount = 1000;
    private int accountBalance;
    private boolean student;
    private int dayOfWeek;
    private static Map<String, Integer> daysToInts = new HashMap<>();  // Map the day of the week name to its corresponding integer representation
    static {
        daysToInts.put("Sunday", 1);
        daysToInts.put("Monday", 2);
        daysToInts.put("Saturday", 7);
    }
    private float expectedFeeRate;

    /**
     * Class Constructor
     */
    public WithdrawalFeesCalculatorTest(boolean student, String dayOfWeekName, int balance, float expectedFeeRate) {
        this.accountBalance = balance;
        this.student = student;
        this.dayOfWeek = daysToInts.get(dayOfWeekName);
        this.expectedFeeRate = expectedFeeRate;
    }

    //Build test cases to be passed into constructor
    @Parameterized.Parameters(name = "TC:{index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                /*Test Case Format: <account Balance, student, dayOfWeek, expectedFee>
                /*TC0*/ {true, "Saturday", 5000, 0f},
                /*TC1*/ {true, "Monday", 5000, 0.001f},
                /*TC2*/ {false, "Saturday", 5000, 0.001f},
                /*TC3*/ {false, "Saturday", 2000000, 0f},
        });
    }

    // Instantiate FeesCalculator object to be tested on
    @Before
    public void init(){
        this.withdrawalFee = new FeesCalculator();
    }

    @Test
    public void WithdrawalFeeTest() {
        int actualFee = withdrawalFee.calculateWithdrawalFee(amount, this.accountBalance, this.student, this.dayOfWeek);
        int expectedFee = Math.round(amount * this.expectedFeeRate);
        assertEquals(expectedFee, actualFee);
    }
}
