package core.BlockChain.Blocks;

import core.HashUtil;

import java.io.Serializable;

abstract public class Block implements Serializable {
    protected byte[] hash;
    protected Hashable data;
    public abstract byte[] calculateHash();
    public Hashable getData() {return  this.data; }
    public byte[] getHash() { return this.hash; }
    public void setHash(byte[] hash) { this.hash = hash; }
    public String getHashAsString() { return HashUtil.byteListToString(this.hash); }
    public abstract boolean isHashValid();
}
