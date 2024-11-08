package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {
    private static final double INITIAL_AMOUNT = 2;
    private static final double DEPOSIT_AMOUNT = 100;
    private static final double NEGATIVE_AMOUNT = -33;
    private static final double WITHDRAW_AMOUNT = 5;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are
    // executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", 1);
        bankAccount = new StrictBankAccount(mRossi, INITIAL_AMOUNT);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(INITIAL_AMOUNT, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the
     * balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        double balanceBeforeDeposit = bankAccount.getBalance();

        bankAccount.deposit(mRossi.getUserID(), DEPOSIT_AMOUNT);
        bankAccount.chargeManagementFees(mRossi.getUserID());

        assertEquals(
                balanceBeforeDeposit + DEPOSIT_AMOUNT - StrictBankAccount.TRANSACTION_FEE
                        - StrictBankAccount.MANAGEMENT_FEE,
                bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bankAccount.withdraw(mRossi.getUserID(), NEGATIVE_AMOUNT);
        });
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bankAccount.withdraw(mRossi.getUserID(), WITHDRAW_AMOUNT);
        });
    }
}
