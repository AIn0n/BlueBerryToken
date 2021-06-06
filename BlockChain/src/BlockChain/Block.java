package BlockChain;

import java.security.MessageDigest;
import HashingUtility.HashingUtility;

public class Block
{
    private final Datable data;
    private byte[] hash;
    private byte[] prevHash;
    private long index;
    private long nonce;

    Block(Datable data, byte[] prevHash)
    {
        this.data = data;
        this.prevHash = prevHash;
        this.calculateHash();
    }

//ask about this or null option
    Block(Datable data)
    {
        this.data = data;
        this.prevHash = null;
        this.calculateHash();
    }

    private byte[] convertToBytes()
    {
        byte[] result = HashingUtility.concatByteLists(
                this.data.getBytes(),
                HashingUtility.longToByteList(this.index)
        );
        result = HashingUtility.concatByteLists(result, HashingUtility.longToByteList(this.nonce));
    //in case of genesis block we do not have any kind of prevHash
        if(this.prevHash != null) result = HashingUtility.concatByteLists(result, this.prevHash);
        return result;
    }

    void calculateHash()
    {
        try
        {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            this.hash = sha256.digest(this.convertToBytes());
        }
        catch (java.security.NoSuchAlgorithmException e) { e.printStackTrace(); }
    }

    void printBlock()
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
    public void setPrevHash(byte[] hash) { this.prevHash = hash; }
    public void setIndex(long index) {this.index = index; }
    public long getIndex() { return this.index; }
}

