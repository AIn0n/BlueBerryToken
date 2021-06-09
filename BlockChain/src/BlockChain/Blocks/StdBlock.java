package BlockChain.Blocks;

import java.security.PublicKey;

import HashingUtility.HashUtil;

public class StdBlock extends Block
{
    private final byte[] prevHash;
    private final PublicKey miner;
    private long nonce = 0;

    public StdBlock(Datable data, Block prevBlock, PublicKey miner)
    {
        this.data = data;
        this.prevHash = prevBlock.getHash();
        this.index = prevBlock.getIndex() + 1;
        this.miner = miner;
    }

    private byte[] convertToBytes()
    {
        return HashUtil.concatByteLists(
            HashUtil.longToByteList(this.index),
            HashUtil.longToByteList(this.nonce),
            this.data.getBytes(),
            this.miner.getEncoded(),
            this.prevHash
        );
    }

    public void calculateHash()
    {
        try { this.hash = HashUtil.hash(this.convertToBytes()); }
        catch (java.security.NoSuchAlgorithmException e) { e.printStackTrace(); }
    }

    public String toString()
    {
        return (
            "message: "     + this.data +
            "\nindex: "     + this.index +
            "\nhash: "      + HashUtil.byteListToString(this.hash) +
            "\nprev hash: " + HashUtil.byteListToString(this.prevHash) + '\n');
    }

    public void setNonce(long nonce) {this.nonce = nonce;}

    public String getPrevHashAsString() { return HashUtil.byteListToString(this.prevHash);}
}