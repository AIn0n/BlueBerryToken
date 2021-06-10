package Miner;

import BlockChain.BlockChain;
import TransactionsData.TokenCounter;
import TransactionsData.Transaction;
import TransactionsData.Transactions;

import java.security.PublicKey;
import java.util.HashMap;

public class BlueBerryMiner extends StandardMiner
{
    private final BlockChain bc;
    private final Transactions transactions;
    private HashMap<PublicKey, Long> wallets;


    public BlueBerryMiner(PublicKey minerKey, BlockChain bc)
    {
        super(minerKey);
        this.bc = bc;
        transactions = new Transactions();
        this.wallets = TokenCounter.countAll(this.bc);
    }

    public void add(Transaction transaction) throws Exception
    {
        TokenCounter.addTransactionsToWallets(wallets, transaction);

        if(wallets.get(transaction.getSender()) >= 0) throw new Exception("Invalid transaction");

        transactions.add(transaction);
    }
}
