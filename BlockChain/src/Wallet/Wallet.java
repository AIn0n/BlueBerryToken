package Wallet;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Transaction.Transactions;
import Transaction.TxOut;
import Validator.TransactionsValidator;

import java.security.KeyPair;
import java.util.HashSet;

public class Wallet
{
    private WalletListener listener;
    private BlockChain blockChain;
    private long Balance;
    private KeyPair keys;

    public Wallet(WalletListener listener, KeyPair keyPair)
    {
        this.listener = listener;
        this.keys = keyPair;
    }

    public void updateBalance()
    {
        HashSet<TxOut> unspent = TransactionsValidator.getUnspentOuts(TransactionsValidator.getAllTransactions(blockChain));
    }

    public static void main(String[] args)
    {

    }
}