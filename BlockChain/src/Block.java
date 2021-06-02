import java.security.MessageDigest;

public class Block
{
    private final Datable data;
    private byte[] hash;
    private byte[] prevHash;

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

    private byte[] convertDataAndPrevHashToBytes()
    {
        if(this.prevHash != null)   //in case of genesis block we do not have any kind of prevHash
        {
        //all of code here is just two bytes lists concat
            byte[] dataAsBytes = this.data.getBytes();
            byte[] bytes = new byte[dataAsBytes.length + this.prevHash.length];

            System.arraycopy(dataAsBytes, 0, bytes, 0, dataAsBytes.length);
            System.arraycopy(this.prevHash, 0, bytes, dataAsBytes.length, this.prevHash.length);

            return bytes;
        }
        return this.data.getBytes();
    }

    void calculateHash()
    {
        try
        {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            this.hash = sha256.digest(this.convertDataAndPrevHashToBytes());
        }
        catch (java.security.NoSuchAlgorithmException e) { e.printStackTrace(); }
    }

    public byte[] getHash() { return hash; }
    public byte[] getPrevHash() { return prevHash; }
    public void setPrevHash(byte[] hash) { this.prevHash = hash; }
}

//not sure for how long this class will be useful, if I will use it more than for debugging
//i create new file for it
class HashingUtility
{
    public static String ByteListToString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}