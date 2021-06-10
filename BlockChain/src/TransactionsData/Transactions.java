package TransactionsData;

import BlockChain.Blocks.Datable;
import Trees.MerkleTree.MerkleTree;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Transactions implements Datable, Iterable<Transaction>
{
    //Linked list to make impossible entering two same transactions
    private final LinkedHashSet<Transaction> transactions = new LinkedHashSet<>();
    private byte[] hash;

    @Override
    public byte[] getBytes() { return this.hash; }

    @Override
    public Iterator<Transaction> iterator() { return transactions.iterator(); }

    public void add(Transaction transaction)
    {
        transactions.add(transaction);
        this.hash = MerkleTree.getMerkleRoot(this.transactions);
    }

    @Override
    public boolean verify()
    {
        for (Transaction n : this.transactions) { if(!n.verify()) return false; }
        return (this.hash == MerkleTree.getMerkleRoot(this.transactions));
    }
}
