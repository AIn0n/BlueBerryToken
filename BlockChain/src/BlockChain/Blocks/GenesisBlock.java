package BlockChain.Blocks;

import HashingUtility.HashingUtility;

import java.security.MessageDigest;

public class GenesisBlock extends Block
{
    public GenesisBlock(Datable data)
    {
        this.data = data;
        this.index = 0;
        calculateHash();
    }

    private byte[] convertToBytes()
    {
        return HashingUtility.concatByteLists(
            HashingUtility.longToByteList(this.index),
            this.data.getBytes());
    }

    @Override
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
            "\nhash: "      + HashingUtility.byteListToString(this.hash) + '\n');
    }
}
