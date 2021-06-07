package BlockChain.Blocks;

import java.io.Serializable;

abstract class Block implements Serializable {
    private byte[] hash;
    private long index;
    private Datable data;
    abstract void calculateHash();
    public byte[] getHash() { return this.hash; }
    public long getIndex() { return this.index; }
}
