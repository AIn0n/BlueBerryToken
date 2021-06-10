package TransactionsData;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;

import java.security.*;
import java.util.HashMap;
import java.util.Map;

public class TokenCounter
{
    public static void addTransactionsToWallets(HashMap<PublicKey, Long> wallets, Transaction transaction)
    {
        long amount = transaction.getAmount();
        wallets.compute(transaction.getSender(),
                (k,v)->(v==null) ? -amount : v-amount);
        wallets.compute(transaction.getRecipient(),
                (k,v)->(v==null) ? amount : v+amount);
    }

    public static HashMap<PublicKey, Long> countAll(Transactions transactions)
    {
        HashMap<PublicKey, Long> wallets = new HashMap<>();
        for(Transaction n: transactions) { addTransactionsToWallets(wallets, n); }
        return wallets;
    }

    public static HashMap<PublicKey, Long> countAll(BlockChain bc)
    {
        HashMap<PublicKey, Long> wallets = new HashMap<>();
        for(Block x: bc)
        {
            Transactions transactions = (Transactions) x.getData();
            for(Transaction y: transactions)
            {
                addTransactionsToWallets(wallets, y);
            }
        }
        return wallets;
    }

    public static long countForKeyFromTransactions(PublicKey pk, Transactions transactions)
    {
        long result = 0L;
        for(Transaction n: transactions)
        {
            if(n.getSender().equals(pk)) result -= n.getAmount();
            if(n.getRecipient().equals(pk)) result += n.getAmount();
        }
        return result;
    }

    public static long countForKey(PublicKey pk, BlockChain bc)
    {
        long result = 0L;
        for(Block n: bc)
        {
            Transactions transactions = (Transactions) n.getData();
            result += countForKeyFromTransactions(pk, transactions);
        }
        return result;
    }

    public static void main(String[] args)
    {
        Transactions transactions = new Transactions();
        try
        {
            KeyPairGenerator KeyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            KeyGen.initialize(1024);
            KeyPair pair1 = KeyGen.genKeyPair();
            KeyPair pair2 = KeyGen.genKeyPair();

            transactions.add(new Transaction(pair1, KeyGen.genKeyPair().getPublic(), 1000, 1, 2));
            transactions.add(new Transaction(pair1, KeyGen.genKeyPair().getPublic(), 1000, 2, 2));
            transactions.add(new Transaction(pair1, pair2.getPublic(), 1000, 3, 2));
            transactions.add(new Transaction(pair1, pair2.getPublic(), 1000, 3, 2));

            HashMap<PublicKey, Long> wallets = TokenCounter.countAll(transactions);
            for(Map.Entry<PublicKey, Long> n: wallets.entrySet())
            {
                System.out.println("user " + n.getKey().hashCode() + " bankroll " + n.getValue());
            }

        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) { e.printStackTrace(); }
    }
}
