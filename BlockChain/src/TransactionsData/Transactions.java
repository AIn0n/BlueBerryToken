package TransactionsData;

import BlockChain.Blocks.Datable;

import java.util.ArrayList;
import java.util.List;

public class Transactions implements Datable {

    ArrayList<Transaction> transactions;

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    public Transactions(ArrayList<Transaction> transactions)
    {
        this.transactions = transactions;
    }
    public void add(Transaction transaction) { transactions.add(transaction); }

    @Override
    public boolean verify()
    {
        for (Transaction n : this.transactions) { if(!n.verify()) return false; }
        return true;
    }
}
