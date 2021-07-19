package Validator;

import core.BlockChain.BlockChain;
import core.MinimalMiner;
import Transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsValidatorTest
{
    private BlockChain bc;
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

        bc = new BlockChain(new Transactions(new Tx(
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
    }

    @DisplayName("Check Signatures and previous outputs for freshly initialized blockchain")
    @Test
    public void checkSignAndOutsForFreshBlockchain()
    {
        assertTrue(
                TransactionsValidator.areSignaturesAndPrevOutsValid(
                        TransactionsValidator.getAllTransactions(bc)));
    }

    @DisplayName("Check balance for freshly initialized blockchain")
    @Test
    public void checkBalancesForFreshlyInitializedBlockchain()
    {
        assertTrue(
                TransactionsValidator.areBalancesValid(
                        TransactionsValidator.getAllTransactions(bc)));
    }

    @DisplayName("Check get unspent outs for freshly initialized blockchain")
    @Test
    public void CheckGetUnspentOutsForFreshBlockchain()
    {
        HashSet<TxOut> unspent = TransactionsValidator.getUnspentOuts(TransactionsValidator.getAllTransactions(bc));
        assertEquals(toNewcomersOuts, unspent);
    }
}