package BlockChain.Blocks;

import java.security.MessageDigest;
import java.security.PublicKey;

import HashingUtility.HashingUtility;

public class StdBlock extends Block
{
    private final byte[] prevHash;
    private final long index;
    private final Datable data;
    private byte[] hash;
    private long nonce = 0;
    private final PublicKey miner;

    public StdBlock(Datable data, Block prevBlock, PublicKey miner)
    {
        this.data = data;
        this.prevHash = prevBlock.getHash();
        this.index = prevBlock.getIndex() + 1;
        this.miner = miner;
    }

//ask about this or null option
    public StdBlock(Datable data)
    {
        this.data = data;
        this.prevHash = HashingUtility.longToByteList(0);
        this.miner = null;
        this.index = 0;
        this.calculateHash();
    }

    private byte[] convertToBytes()
    {
        byte[] result = HashingUtility.concatByteLists(
            HashingUtility.longToByteList(this.index),
            HashingUtility.longToByteList(this.nonce),
            this.data.getBytes(),
            this.prevHash
        );
    //in case of genesis block we do not have any kind of prevHash or miner
        if(this.miner != null) result = HashingUtility.concatByteLists(result, this.miner.getEncoded());
        return result;
    }

    public void calculateHash()
    {
        try
        {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            this.hash = sha256.digest(this.convertToBytes());
        }
        catch (java.security.NoSuchAlgorithmException e) { e.printStackTrace(); }
    }

    public void printBlock()
    {
        System.out.println(
            "message: " + this.data +
            "\nindex: " + this.index +
            "\nhash: " + HashingUtility.byteListToString(this.hash) +
            "\nprev hash: " + HashingUtility.byteListToString(this.prevHash));
        System.out.println();
    }

    public void setNonce(long nonce) {this.nonce = nonce;}
}