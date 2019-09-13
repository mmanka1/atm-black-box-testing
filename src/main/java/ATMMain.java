import atm.ATM;
import banking.Money;

public class ATMMain {

	public static void main(String[] args) {
		try {
			ATM theATM = new ATM(0, "London", "CIBC Branch", null, true, 1234, "1234", 0, 0, 1, new Money(20));

			new Thread(theATM).start();

			Thread.sleep(5000);

			System.out.println("Switch ATM ON.");
			theATM.switchOn();

			Thread.sleep(5000);

			System.out.println("Set initial cash available.");
			theATM.getCashDispenser().setInitialCash(new Money(2000));
			System.out.println("Connect to bank.");
			theATM.getNetworkToBank().openConnection();

			theATM.cardInserted();

			Thread.sleep(5000);

			System.out.println("Switch ATM OFF.");
			theATM.switchOff();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
