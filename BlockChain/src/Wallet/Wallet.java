package Wallet;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Transaction.Transactions;
import Transaction.Tx;
import Transaction.TxOut;
import Validator.TransactionsValidator;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashSet;

public class Wallet
{
    private final WalletListener listener;
    private BlockChain blockChain;
    private long balance;
    private final KeyPair keys;

    public Wallet(WalletListener listener, KeyPair keyPair)
    {
        this.listener = listener;
        this.keys = keyPair;
        this.blockChain = listener.getBlockChain();
        this.balance = 0;
    }

    public boolean isBlockChainUpToDate()
    {
        return Arrays.equals(blockChain.last().getHash(), listener.getLastBlockHash());
    }

    public void getBcFromListener()
    {
            this.blockChain = listener.getBlockChain();
    }


    public void updateBalance()
    {
        if(!isBlockChainUpToDate())
            getBcFromListener();
        HashSet<TxOut> unspent = TransactionsValidator.getUnspentOuts(
                TransactionsValidator.getAllTransactions(blockChain));

        int updatedBalance = 0;
        for(TxOut txOut: unspent)
            if(txOut.getRecipient().equals(keys.getPublic()))
                updatedBalance += txOut.getAmount();

        this.balance = updatedBalance;
    }

    public long getBalance() { return balance; }
}