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
    private static int amount = 800;
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
    public WithdrawalFeesCalculatorTest(int balance, boolean student, String dayOfWeekName, float expectedFeeRate) {
        this.accountBalance = balance;
        this.student = student;
        this.dayOfWeek = daysToInts.get(dayOfWeekName);
        this.expectedFeeRate = expectedFeeRate;
    }

    //Build test cases to be passed into constructor
    @Parameterized.Parameters(name = "TC:{index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{ {999, true,  "Saturday", 0f}, {1000, true, "Saturday", 0f},
                {1001,	true,	"Saturday", 0f}, {5000,	true,	"Saturday", 0f}, {9998,	true,	"Saturday", 0f},
                {9999,	true,	"Saturday", 0f}, {10000,	true,	"Saturday", 0f}, {999,	false,	"Saturday", 0.002f},
                {1000,	false,	"Saturday", 0.001f}, {1001,	false,	"Saturday", 0.001f}, {5000,	false,	"Saturday", 0.001f},
                {9998,	false,	"Saturday", 0.001f}, {9999,	false,	"Saturday", 0.001f}, {10000,	false,	"Saturday", 0f},
                {999,	true,	"Sunday", 0f}, {1000,	true,	"Sunday", 0f}, {1001,	true,	"Sunday", 0f},
                {5000,	true,	"Sunday", 0f}, {9998,	true,	"Sunday",0f}, {9999,	true,	"Sunday", 0f},
                {10000,	true,	"Sunday", 0f}, {999,	false,	"Sunday", 0.002f}, {1000,	false,	"Sunday", 0.001f},
                {1001,	false,	"Sunday", 0.001f}, {5000,	false,	"Sunday", 0.001f}, {9998,	false,	"Sunday", 0.001f},
                {9999,	false,	"Sunday", 0.001f}, {10000,	false,	"Sunday", 0f}, {999,	true,	"Monday", 0.001f},
                {1000,	true,	"Monday", 0.001f}, {1001,	true,	"Monday", 0.001f}, {5000,	true,	"Monday", 0.001f},
                {9998,	true,	"Monday", 0.001f}, {9999,	true,	"Monday", 0.001f}, {10000,	true,	"Monday", 0.001f},
                {999,	false,	"Monday", 0.002f}, {1000,	false,	"Monday", 0.001f}, {1001,	false,	"Monday", 0.001f},
                {5000,	false,	"Monday", 0.001f}, {9998,	false,	"Monday", 0.001f}, {9999,	false,	"Monday", 0.001f},
                {10000,	false,	"Monday", 0f}
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
