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

    private long getInSum()
    {
        long inSum = 0;
        for(TxIn n: this.ins) { inSum += n.getAmount(); }
        return inSum;
    }

    private long getOutSum()
    {
        long outSum = 0;
        for(TxOut n: this.outs) { outSum += n.getAmount(); }
        return outSum;
    }

    public boolean isBalanceValid()
    {
        return (getInSum() == getOutSum());
    }

    @Override
    public byte[] getHash() {
        return this.hash;
    }

    public byte[] calculateHash() { return HashUtil.hash(getBytes()); }
}

