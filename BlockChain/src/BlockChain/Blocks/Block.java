package BlockChain.Blocks;

import HashingUtility.HashingUtility;

import java.io.Serializable;

abstract public class Block implements Serializable {
    protected byte[] hash;
    protected long index;
    protected Datable data;
    public abstract void calculateHash();
    public byte[] getHash() { return this.hash; }
    public String getHashAsString() { return HashingUtility.byteListToString(this.hash); }
    public long getIndex() { return this.index; }
}
