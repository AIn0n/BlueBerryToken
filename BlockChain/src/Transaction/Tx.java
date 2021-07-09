package Transaction;

import BlockChain.Blocks.Hashable;
import HashingUtility.HashUtil;

public class Tx implements Hashable {
    private final Iterable<TxIn> inputTx;
    private final Iterable<TxOut> outputTx;
    private final byte[] hash;

    public Tx(Iterable<TxIn> in, Iterable<TxOut> out) {
        this.inputTx = in;
        this.outputTx = out;
        this.hash = calculateHash();
    }

    public Iterable<TxOut> getOutputTx() { return outputTx; }

    public byte[] getBytes() {
        byte[] result = new byte[0];
        for (TxIn n : inputTx)      { result = HashUtil.concatTwoByteLists(result, n.getBytes()); }
        for (TxOut n : outputTx)    { result = HashUtil.concatTwoByteLists(result, n.getBytes()); }
        return result;
    }

    private long getInSum()
    {
        long inSum = 0;
        for(TxIn n: this.inputTx) { inSum += n.getAmount(); }
        return inSum;
    }

    private long getOutSum()
    {
        long outSum = 0;
        for(TxOut n: this.outputTx) { outSum += n.getAmount(); }
        return outSum;
    }

    public boolean validate()
    {
        return (getInSum() == getOutSum());
    }

    @Override
    public byte[] getHash() {
        return this.hash;
    }

    public byte[] calculateHash() { return HashUtil.hash(getBytes()); }
}

