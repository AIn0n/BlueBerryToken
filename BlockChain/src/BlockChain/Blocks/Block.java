package BlockChain.Blocks;

import HashingUtility.HashUtil;

import java.io.Serializable;

abstract public class Block implements Serializable {
    protected byte[] hash;
    protected Datable data;
    public abstract byte[] calculateHash();
    public Datable getData() {return  this.data; }
    public byte[] getHash() { return this.hash; }
    public void setHash(byte[] hash) { this.hash = hash; }
    public String getHashAsString() { return HashUtil.byteListToString(this.hash); }
    public abstract boolean verify();
}
