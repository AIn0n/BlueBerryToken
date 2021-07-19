package Wallet;

import BlockChain.BlockChain;
import Miner.MinimalMiner;
import Transaction.Transactions;
import Transaction.Tx;
import Transaction.TxIn;
import Transaction.TxOut;
import Validator.TransactionsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private LocalWalletListener listener;
    public int numOfNewcomers = 4;
    public long givingAwayBalance = 1000;
    public KeyPair[] keyPairs;
    public HashSet<TxOut> toNewcomersOuts;
    @BeforeEach
    public void setUp() throws NoSuchProviderException, NoSuchAlgorithmException
    {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");
        kpg.initialize(1024);

        //lets assume that first keys are belong to crypto owner - rest to the newcomers
        keyPairs = new KeyPair[numOfNewcomers];
        for (int i = 0; i < keyPairs.length; i++)
            keyPairs[i] = kpg.generateKeyPair();
        //first transaction in blockchain, total amount of tokens, all goes into owner
        TxOut firstTx = new TxOut(keyPairs[0].getPublic(), givingAwayBalance * numOfNewcomers, 0);

        BlockChain bc = new BlockChain(new Transactions(new Tx(
                new HashSet<>(),
                new HashSet<TxOut>() {{ add(firstTx); }})));

        //next transaction is giving away tokens from owner to newcomers
        TxIn fromOwnerIn = new TxIn(firstTx.getHash(), firstTx.getAmount());
        fromOwnerIn.sign(keyPairs[0].getPrivate());
        toNewcomersOuts = new HashSet<TxOut>(){{
            for (int i = 0; i < keyPairs.length; i++)
                add(new TxOut(keyPairs[i].getPublic(), givingAwayBalance, i+1));
        }};

        //adding and mining transaction from owner to every newcomer
        bc.add(MinimalMiner.mine(new Transactions(new Tx(
                new HashSet<TxIn>(){{add(fromOwnerIn);}},
                toNewcomersOuts)), bc.last().getHash()));
        //at the end we have fully initialized blockchain ready for tests!
        listener = new LocalWalletListener(bc);
    }

    @DisplayName("check balance for newcomers in fresh blockchain")
    @Test
    public void CheckNewcomersBalance()
    {
        for(int i = 1; i < keyPairs.length; i++)
        {
            Wallet wallet = new Wallet(listener, keyPairs[i]);
            wallet.updateBalance();
            assertEquals(givingAwayBalance, wallet.getBalance());
        }
    }

    @DisplayName("check function for sending tokens in fresh blockchain")
    @Test
    public void checkTokenSendFunction()
    {
        Wallet sender = new Wallet(listener, keyPairs[1]);
        Wallet receiver = new Wallet(listener, keyPairs[2]);
        sender.sendTokens(receiver.getKeys().getPublic(), 100);
        receiver.updateBalance();
        sender.updateBalance();
        assertEquals(givingAwayBalance+100, receiver.getBalance());
        assertEquals(givingAwayBalance-100, sender.getBalance());

        assertTrue(TransactionsValidator.validate(listener.getBlockChain()));
    }
}