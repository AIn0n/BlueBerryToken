package Transaction;

import BlockChain.Blocks.Hashable;
import Trees.MerkleTree.MerkleTree;

import java.util.HashSet;

public class Transactions implements Hashable
{
    private HashSet<Tx> transactions;

    public Transactions() { this.transactions = new HashSet<>(1024); }

    public void add(Tx transaction)
    {
        this.transactions.add(transaction);
    }

    @Override
    public byte[] getHash() {
        return MerkleTree.getMerkleRoot(transactions);
    }
}
