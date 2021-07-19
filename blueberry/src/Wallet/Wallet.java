package Wallet;

import core.BlockChain.BlockChain;
import Transaction.*;
import Validator.TransactionsValidator;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Wallet
{
    private final WalletListener listener;
    private BlockChain blockChain;
    private final KeyPair keys;
    private long balance = 0;
    private final Random rng = new Random();

    public Wallet(WalletListener listener, KeyPair keyPair)
    {
        this.listener = listener;
        this.keys = keyPair;
        this.blockChain = listener.getBlockChain();
    }

    public boolean isBlockChainUpToDate()
    {
        return Arrays.equals(
                blockChain.last().getHash(),
                listener.getLastBlockHash());
    }

    public void getBcFromListener()
    {
        this.blockChain = listener.getBlockChain();
    }

    private  HashSet<TxOut> getAllUnspentOuts()
    {
        return TransactionsValidator.getUnspentOuts(
                TransactionsValidator.getAllTransactions(blockChain));
    }

    private HashSet<TxOut> getUserUnspentOuts()
    {
        HashSet<TxOut> userUnspent = new HashSet<>();
        for(TxOut txOut: getAllUnspentOuts())
        {
            if(txOut.getRecipient().equals(keys.getPublic()))
                userUnspent.add(txOut);
        }
        return userUnspent;
    }

    public void sendTokens(PublicKey recipient, long amount)
    {
        updateBalance();
        HashSet<TxOut> unspents = getUserUnspentOuts();
        HashSet<TxIn> inputsFromUnspents = new HashSet<>();
        for(TxOut txOut: unspents)
        {
            TxIn temp = new TxIn(txOut.getHash(), txOut.getAmount());
            temp.sign(keys.getPrivate());
            inputsFromUnspents.add(temp);
        }
        listener.sendTx(new Tx(inputsFromUnspents, new HashSet<TxOut>(){{
            add(new TxOut(recipient, amount, rng.nextLong()));
            add(new TxOut(keys.getPublic(), balance - amount, rng.nextLong()));
        }}));
    }

    public void updateBalance()
    {
        if(!isBlockChainUpToDate())
            getBcFromListener();

        int updatedBalance = 0;
        for(TxOut txOut: getUserUnspentOuts())
            updatedBalance += txOut.getAmount();

        this.balance = updatedBalance;
    }

    public long getBalance() { return balance; }
    public KeyPair getKeys() { return keys; }
}