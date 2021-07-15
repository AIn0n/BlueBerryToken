package Transaction;

import BlockChain.Blocks.Hashable;
import HashingUtility.HashUtil;

public class Tx implements Hashable {
    private final Iterable<TxIn> ins;
    private final Iterable<TxOut> outs;
    private final byte[] hash;

    public Tx(Iterable<TxIn> in, Iterable<TxOut> out) {
        this.ins = in;
        this.outs = out;
        this.hash = calculateHash();
    }

    public Iterable<TxOut> getOuts() { return outs; }
    public Iterable<TxIn> getIns() { return ins; }

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

