package com.wallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {

    private Wallet wallet;

    @BeforeEach
    public void setUp() {
        wallet = new Wallet("w123", new BigDecimal("100.00"));
    }

    @Test
    public void testInitialBalance() {
        assertEquals(new BigDecimal("100.00"), wallet.getBalance());
        assertEquals("w123", wallet.getWalletId());
    }

    @Test
    public void testNegativeInitialBalanceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Wallet("w124", new BigDecimal("-50.00"));
        });
    }

    @Test
    public void testDepositPositiveAmount() {
        wallet.deposit(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), wallet.getBalance());
    }

    @Test
    public void testDepositNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            wallet.deposit(new BigDecimal("-10.00"));
        });
    }

    @Test
    public void testDepositZeroAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            wallet.deposit(BigDecimal.ZERO);
        });
    }

    @Test
    public void testWithdrawValidAmount() {
        wallet.withdraw(new BigDecimal("30.00"));
        assertEquals(new BigDecimal("70.00"), wallet.getBalance());
    }

    @Test
    public void testWithdrawInsufficientFundsThrowsException() {
        assertThrows(InsufficientFundsException.class, () -> {
            wallet.withdraw(new BigDecimal("150.00"));
        });
    }

    @Test
    public void testWithdrawNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            wallet.withdraw(new BigDecimal("-20.00"));
        });
    }
    
    @Test
    public void testWithdrawZeroAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            wallet.withdraw(BigDecimal.ZERO);
        });
    }

    @Test
    public void testMultipleTransactions() {
        wallet.deposit(new BigDecimal("50.00"));
        wallet.withdraw(new BigDecimal("20.00"));
        wallet.deposit(new BigDecimal("10.50"));
        assertEquals(new BigDecimal("140.50"), wallet.getBalance());
    }
}
