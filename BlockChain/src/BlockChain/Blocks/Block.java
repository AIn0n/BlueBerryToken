package BlockChain.Blocks;

import HashingUtility.HashUtil;

import java.io.Serializable;

abstract public class Block implements Serializable {
    protected byte[] hash;
    protected Datable data;
    public abstract void calculateHash();
    public byte[] getHash() { return this.hash; }
    public String getHashAsString() { return HashUtil.byteListToString(this.hash); }
}
