package BlockChain;

import java.security.MessageDigest;
import java.security.PublicKey;

import HashingUtility.HashingUtility;

public class Block
{
    private final byte[] prevHash;
    private final long index;
    private final Datable data;
    private byte[] hash;
    private long nonce = 0;
    private PublicKey miner;

//genesis block - custom constructor or new class extending Block?
    public Block(Datable data, byte[] prevHash)
    {
        this.data = data;
        this.prevHash = prevHash;
        this.index = 0;
        this.calculateHash();
    }

    public Block(Datable data, Block prevBlock, PublicKey miner)
    {
        this.data = data;
        this.prevHash = prevBlock.getHash();
        this.index = prevBlock.getIndex() + 1;
        this.miner = miner;
    }

//ask about this or null option
    public Block(Datable data)
    {
        this.data = data;
        this.prevHash = null;
        this.index = 0;
        this.calculateHash();
    }

    private byte[] convertToBytes()
    {
        byte[] result = HashingUtility.concatByteLists(
            HashingUtility.longToByteList(this.index),
            HashingUtility.longToByteList(this.nonce),
            this.data.getBytes()
        );
    //in case of genesis block we do not have any kind of prevHash
        if(this.prevHash != null)
        {
            result = HashingUtility.concatByteLists(result, this.prevHash, this.miner.getEncoded());
        }
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
            "\nhash: " + HashingUtility.byteListToString(this.hash));

        if(this.prevHash != null)
            System.out.println("prev hash: " + HashingUtility.byteListToString(this.prevHash));
        System.out.println();
    }

    public byte[] getHash() { return hash; }
    public void setNonce(long nonce) {this.nonce = nonce;}
    public long getIndex() { return this.index; }
}

