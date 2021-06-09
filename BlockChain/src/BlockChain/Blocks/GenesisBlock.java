package BlockChain.Blocks;

import HashingUtility.HashUtil;

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
        return HashUtil.concatTwoByteLists(
            HashUtil.longToByteList(this.index),
            this.data.getBytes());
    }

    @Override
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
            "\nhash: "      + HashUtil.byteListToString(this.hash) + '\n');
    }
}
