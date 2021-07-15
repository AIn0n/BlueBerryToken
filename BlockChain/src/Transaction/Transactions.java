package Transaction;

import BlockChain.Blocks.Hashable;
import Trees.MerkleTree.MerkleTree;

import java.util.HashSet;

public class Transactions implements Hashable
{
    private final HashSet<Tx> transactions;

    public Transactions() { this.transactions = new HashSet<>(); }

    public Transactions(Tx tx)
    {
        this.transactions = new HashSet<>();
        this.transactions.add(tx);
    }

    public void add(Tx transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public byte[] getHash() {
        return MerkleTree.getMerkleRoot(transactions);
    }

    public HashSet<Tx> getTxHashSet()
    {
        return this.transactions;
    }
}
