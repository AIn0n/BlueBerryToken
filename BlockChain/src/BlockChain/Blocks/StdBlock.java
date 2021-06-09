package BlockChain.Blocks;

import java.security.PublicKey;
import java.util.Arrays;

import HashingUtility.HashUtil;

public class StdBlock extends Block
{
    private final byte[] prevHash;
    private final PublicKey miner;
    private long nonce = 0;

    public StdBlock(Datable data, byte[] prevHash, PublicKey miner)
    {
        this.data = data;
        this.prevHash = prevHash;
        this.miner = miner;
    }

    private byte[] convertToBytes()
    {
        return HashUtil.concatByteLists(
            HashUtil.longToByteList(this.nonce),
            this.data.getBytes(),
            this.miner.getEncoded(),
            this.prevHash
        );
    }

    public byte[] calculateHash()
    {
        try { return HashUtil.hash(this.convertToBytes()); }
        catch (java.security.NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean verify() { return data.verify() && Arrays.equals(calculateHash(), this.hash); }

    public String toString()
    {
        return (
            "message: "     + this.data +
            "\nhash: "      + HashUtil.byteListToString(this.hash) +
            "\nprev hash: " + HashUtil.byteListToString(this.prevHash) + '\n');
    }

    public void setNonce(long nonce) {this.nonce = nonce;}

    public String getPrevHashAsString() { return HashUtil.byteListToString(this.prevHash);}
}