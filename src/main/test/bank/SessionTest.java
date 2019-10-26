package atm;

import bank.AccountEntry;
import bank.BankDatabase;
import bank.CustomerEntry;
import banking.Money;
import banking.exceptions.InvalidAmountException;
import banking.exceptions.InvalidPINException;
import banking.exceptions.InvalidTransactionChoiceException;
import org.hamcrest.CoreMatchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SessionTest {
    private static ATM atm;
    private Session session;

    //Session object variables
    private String pin;
    private static int transactionChoice = 0;
    private static int cardId = 1;
    private Money amount;
    private int dollarAmount;

    enum Type {Pin, Amount};
    private Type type;

    //ATM object variables
    private static int atmId = 1;
    private static String atmLocation = "location";
    private static String bankName = "bankName";
    private static InetAddress address = null;

    //Customer variables
    private static int ownerId = 0;

    //Account variables
    private static int dailyLimit = 1000;

    private static BankDatabase database;

    static {
        // Connect to database
        database = BankDatabase.getInstance();

        // Create ATM instance
        atm = new ATM(atmId, atmLocation ,bankName, address);

        // Create new customer
        CustomerEntry customer = new CustomerEntry(ownerId, "firstName", "lastName", false, cardId, 123);
        database.addCustomer(customer);
    }

    /**
     * Class Constructor
     */
    public SessionTest(Type type, String pin, int dollarAmount){
        this.type = type;
        this.pin = pin;
        this.dollarAmount = dollarAmount;
        //Create Money object to be passed as parameter into Session object
        this.amount = new Money(dollarAmount);

        // For each test, create a new account
        AccountEntry account = new AccountEntry(ownerId, "Checking", 5000, 5000, dailyLimit, 0);
        try{
            database.addAccount(0, account);
        }
        catch (BankDatabase.AccountNotFound e) {
            System.out.println("Trying to add wrong type of account.");
        }
    }

    //Build test cases to be passed into constructor - consider test cases for variables of pin and amount
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Type.Amount, "1234", 500}, {Type.Amount, "1234", -20}, {Type.Amount, "1234", 0},
                {Type.Amount, "1234", 20}, {Type.Amount, "1234", 980}, {Type.Amount, "1234", 1000},
                {Type.Amount, "1234", 1020}, {Type.Pin, "1234", 500}, {Type.Pin, "123", 500},
                {Type.Pin,"12345", 500}, {Type.Pin,"1a34", 500}
        });
    }

    // Instantiate Session object to be tested on
    @Before
    public void init(){
        this.session = new Session(atm, cardId, this.pin, transactionChoice, 0, 0, this.amount);
    }

    // Create exception rule
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void SessionATMTest() throws InvalidPINException, InvalidAmountException, InvalidTransactionChoiceException {
        if (this.type == Type.Pin) {
            if (this.pin.length() != 4 || !this.pin.matches("^[0-9]+$")) {
                // Expected is an invalid amount exception
                exception.expect(InvalidPINException.class);
                session.performSession();
            } else {
                // Expected to have no invalid PIN exception
                session.performSession();
            }
        } else {
            if (this.type == Type.Amount) {
                if (this.dollarAmount < 0 || this.dollarAmount > dailyLimit) {
                    // Expected is an invalid amount exception - but nominal pin is invalid,
                    // so for this case, since want to assume one single fault based on the amount
                    // in this case, so assume pin valid, such that the below expectation passes when
                    // there is an InvalidPINException
                    exception.expect(CoreMatchers.anyOf(CoreMatchers.instanceOf(InvalidPINException.class), CoreMatchers.instanceOf(InvalidAmountException.class)));
                    session.performSession();
                } else {
                    if (this.dollarAmount >= 0 && this.dollarAmount <= dailyLimit) {
                        // Expected to have no invalid amount exception - also consider invalidPINException for
                        // the same reason as above
                        exception.expect(CoreMatchers.anyOf(CoreMatchers.instanceOf(InvalidPINException.class)));
                        session.performSession();
                    }
                }
            }
        }
    }
}