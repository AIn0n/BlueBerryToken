package BlockChain.Blocks;

import java.io.Serializable;

abstract public class Block implements Serializable {
    protected byte[] hash;
    protected long index;
    protected Datable data;
    public abstract void calculateHash();
    public byte[] getHash() { return this.hash; }
    public long getIndex() { return this.index; }
}
