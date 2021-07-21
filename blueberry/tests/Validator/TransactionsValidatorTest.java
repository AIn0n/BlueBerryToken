package Validator;

import InitBlueBerryToken.BlueBerryInit;
import Wallet.Wallet;
import Wallet.LocalWalletListener;
import core.BlockChain.BlockChain;
import core.MinimalMiner;
import Transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsValidatorTest
{
    private BlockChain bc;
    public int numOfNewcomers = 4;
    public long givingAwayBalance = 1000;
    public Tx firstTx;
    public HashSet<TxOut> toNewcomersOuts;
    public ArrayList<TxOut> newcomersOutsArr = new ArrayList<>();
    public ArrayList<KeyPair> keys;
    public LocalWalletListener listener;
    @BeforeEach
    public void setUp() throws NoSuchProviderException, NoSuchAlgorithmException
    {
        Random rng = new Random();
        //lets assume that first keys are belong to crypto owner - rest to the newcomers
        keys = BlueBerryInit.generateKeyPairs(numOfNewcomers+1);
        KeyPair owner = keys.remove(0);

        TxOut firstTxOut = new TxOut(owner.getPublic(), givingAwayBalance * numOfNewcomers, rng.nextLong());
        firstTx = new Tx(
                new HashSet<>(),
                new HashSet<TxOut>() {{ add(firstTxOut);}});
        bc = new BlockChain(new Transactions(firstTx));

        //next transaction is giving away tokens from owner to newcomers
        TxIn fromOwnerIn = new TxIn(firstTxOut.getHash(), firstTxOut.getAmount());
        fromOwnerIn.sign(owner.getPrivate());
        for(KeyPair kp: keys)
        {
            newcomersOutsArr.add(new TxOut(kp.getPublic(), givingAwayBalance, rng.nextLong()));
        }
        toNewcomersOuts = new HashSet<>(newcomersOutsArr);

        //adding and mining transaction from owner to every newcomer
        bc.add(MinimalMiner.mine(new Transactions(new Tx(
                new HashSet<TxIn>(){{add(fromOwnerIn);}},
                toNewcomersOuts)), bc.last().getHash()));
        //at the end we have fully initialized blockchain ready for tests!
        listener = new LocalWalletListener(bc);
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

    @DisplayName("check validate function with new, invalid transaction")
    @Test
    public void checkValidationForIncorrectBalance()
    {
        Wallet wallet = new Wallet(listener, keys.get(0));
        wallet.sendTokens(keys.get(1).getPublic(), givingAwayBalance*100);
        assertFalse(TransactionsValidator.validate(bc));
    }

    @DisplayName("check getting init transaction from blockchain")
    @Test
    public void checkFindInitTransaction()
    {
        assertEquals(firstTx, TransactionsValidator.findInitTransaction(bc));
    }

    @DisplayName("check validation with invalid prevOut in TxIn")
    @Test
    public void CheckValidationForInvalidTxOutInTxIn()
    {
        TxIn in = new TxIn(newcomersOutsArr.get(0).getHash(), 2000);
        in.sign(keys.get(0).getPrivate());
        bc.add(MinimalMiner.mine(new Transactions(
                new Tx(
                        new HashSet<TxIn>(){{add(in);}},
                        new HashSet<TxOut>(){{add(new TxOut(keys.get(0).getPublic(),2000, 23));}})),
                bc.last().getHash()));
        assertFalse(TransactionsValidator.validate(bc));
    }

    @DisplayName("check two times used txOut case")
    @Test
    public void checkValidationForReusedOuts()
    {
        TxIn in = new TxIn(newcomersOutsArr.get(0).getHash(), 1000);
        in.sign(keys.get(0).getPrivate());
        bc.add(MinimalMiner.mine(new Transactions(
                        new Tx(
                                new HashSet<TxIn>(){{add(in);}},
                                new HashSet<TxOut>(){{add(new TxOut(keys.get(0).getPublic(),1000, 23));}})),
                bc.last().getHash()));
        bc.add(MinimalMiner.mine(new Transactions(
                        new Tx(
                                new HashSet<TxIn>(){{add(in);}},
                                new HashSet<TxOut>(){{add(new TxOut(keys.get(0).getPublic(),1000, 23));}})),
                bc.last().getHash()));
        assertFalse(TransactionsValidator.validate(bc));
    }
}