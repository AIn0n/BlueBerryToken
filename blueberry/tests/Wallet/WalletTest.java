package Wallet;

import InitBlueBerryToken.BlueBerryInit;
import Validator.TransactionsValidator;
import core.BlockChain.BlockChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private LocalWalletListener listener;
    public int numOfNewcomers = 4;
    public long givingAwayBalance = 1000;
    public ArrayList<KeyPair> keys;
    @BeforeEach
    public void setUp() throws NoSuchProviderException, NoSuchAlgorithmException
    {
        keys = BlueBerryInit.generateKeyPairs(numOfNewcomers+1);
        KeyPair owner = keys.remove(0);
        BlockChain bc = BlueBerryInit.generateBlockChain(owner, keys, givingAwayBalance);
        listener = new LocalWalletListener(bc);
    }

    @DisplayName("check balance for newcomers in fresh blockchain")
    @Test
    public void CheckNewcomersBalance()
    {
        for(int i = 1; i < keys.size(); i++)
        {
            Wallet wallet = new Wallet(listener, keys.get(i));
            wallet.updateBalance();
            assertEquals(givingAwayBalance, wallet.getBalance());
        }
    }

    @DisplayName("check function for sending tokens in fresh blockchain")
    @Test
    public void checkTokenSendFunction()
    {
        Wallet sender = new Wallet(listener, keys.get(1));
        Wallet receiver = new Wallet(listener, keys.get(2));
        sender.sendTokens(receiver.getKeys().getPublic(), 100);
        receiver.updateBalance();
        sender.updateBalance();
        assertEquals(givingAwayBalance+100, receiver.getBalance());
        assertEquals(givingAwayBalance-100, sender.getBalance());

        assertTrue(TransactionsValidator.validate(listener.getBlockChain()));
    }
}