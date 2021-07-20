package Validator;

import InitBlueBerryToken.BlueBerryInit;
import core.BlockChain.BlockChain;
import core.MinimalMiner;
import Transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsValidatorTest
{
    private BlockChain bc;
    public int numOfNewcomers = 4;
    public long givingAwayBalance = 1000;
    public HashSet<TxOut> toNewcomersOuts;
    @BeforeEach
    public void setUp() throws NoSuchProviderException, NoSuchAlgorithmException
    {
        //lets assume that first keys are belong to crypto owner - rest to the newcomers
        ArrayList<KeyPair> keys = BlueBerryInit.generateKeyPairs(numOfNewcomers+1);
        KeyPair owner = keys.remove(0);

        TxOut firstTx = new TxOut(owner.getPublic(), givingAwayBalance * numOfNewcomers, 0);
        bc = BlueBerryInit.initBcWithTxOut(firstTx);

        //next transaction is giving away tokens from owner to newcomers
        TxIn fromOwnerIn = new TxIn(firstTx.getHash(), firstTx.getAmount());
        fromOwnerIn.sign(owner.getPrivate());
        toNewcomersOuts = new HashSet<TxOut>(){{
            for (int i = 0; i < keys.size(); i++)
                add(new TxOut(keys.get(i).getPublic(), givingAwayBalance, i + 1));
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