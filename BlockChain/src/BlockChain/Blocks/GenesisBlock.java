package BlockChain.Blocks;

import HashingUtility.HashUtil;

public class GenesisBlock extends Block
{
    public GenesisBlock(Datable data)
    {
        this.data = data;
        this.hash = calculateHash();
    }

    @Override
    public byte[] calculateHash()
    {
        try { return HashUtil.hash(this.data.getBytes()); }
        catch (java.security.NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    public String toString()
    {
        return (
            "message: "     + this.data +
            "\nhash: "      + HashUtil.byteListToString(this.hash) + '\n');
    }

    @Override
    public boolean verify() { return true; }
}
