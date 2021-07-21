package Transaction;

import core.BlockChain.Blocks.Hashable;
import core.HashUtil;

import java.util.HashSet;

public class Tx implements Hashable {
    private final HashSet<TxIn> ins;
    private final HashSet<TxOut> outs;
    private final byte[] hash;

    public Tx(HashSet<TxIn> in, HashSet<TxOut> out) {
        this.ins = in;
        this.outs = out;
        this.hash = calculateHash();
    }

    public HashSet<TxOut> getOuts() { return outs; }
    public HashSet<TxIn> getIns() { return ins; }

    public byte[] getBytes() {
        byte[] result = new byte[0];
        for (TxIn n : ins)      { result = HashUtil.concatTwoByteLists(result, n.getBytes()); }
        for (TxOut n : outs)    { result = HashUtil.concatTwoByteLists(result, n.getBytes()); }
        return result;
    }

    public long getInSum() throws IllegalArgumentException
    {
        long inSum = 0;
        for(TxIn n: this.ins)
        {
            long amount = n.getAmount();
            if(amount <= 0) throw new IllegalArgumentException();
            inSum += amount;
        }
        return inSum;
    }

    public long getOutSum() throws IllegalArgumentException
    {
        long outSum = 0;
        for(TxOut n: this.outs)
        {
            long amount = n.getAmount();
            if(amount <= 0) throw new IllegalArgumentException();
            outSum += amount;
        }
        return outSum;
    }

    public boolean isBalanceValid()
    {
        try { return getInSum() == getOutSum(); }
        catch (IllegalArgumentException e) { return false; }
    }

    @Override
    public byte[] getHash() {
        return this.hash;
    }

    public byte[] calculateHash() { return HashUtil.hash(getBytes()); }
}

