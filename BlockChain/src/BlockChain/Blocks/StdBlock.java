package BlockChain.Blocks;

import java.security.MessageDigest;
import java.security.PublicKey;

import HashingUtility.HashingUtility;

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
        return HashingUtility.concatByteLists(
            HashingUtility.longToByteList(this.index),
            HashingUtility.longToByteList(this.nonce),
            this.data.getBytes(),
            this.miner.getEncoded(),
            this.prevHash
        );
    }

    public void calculateHash()
    {
        try
        {
            this.hash = MessageDigest
                    .getInstance("SHA-256")
                    .digest(this.convertToBytes());
        }
        catch (java.security.NoSuchAlgorithmException e) { e.printStackTrace(); }
    }

    public String toString()
    {
        return (
            "message: "     + this.data +
            "\nindex: "     + this.index +
            "\nhash: "      + HashingUtility.byteListToString(this.hash) +
            "\nprev hash: " + HashingUtility.byteListToString(this.prevHash) + '\n');
    }

    public void setNonce(long nonce) {this.nonce = nonce;}

    public String getPrevHashAsString() { return HashingUtility.byteListToString(this.prevHash);}
}