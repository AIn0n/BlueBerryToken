package TransactionsData;

import BlockChain.Blocks.Datable;
import Trees.MerkleTree.MerkleTree;

import java.util.ArrayList;
import java.util.List;

public class Transactions implements Datable {

    ArrayList<Transaction> transactions;
    byte[] hash;

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    public Transactions(ArrayList<Transaction> in)
    {
        this.transactions = in;
        this.hash = MerkleTree.getMerkleRoot(in);
    }
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
