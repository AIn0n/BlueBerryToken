import java.security.MessageDigest;

public class Block
{
    private final Datable data;
    private byte[] hash;
    private byte[] prevHash;
    private long index;

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

//not sure for how long this class will be useful, if I will use it more than for debugging
//i create new file for it
class HashingUtility
{
    public static String byteListToString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes) { sb.append(String.format("%02x", b)); }
        return sb.toString();
    }

    public static long byteListToLong(byte[] bytes)
    {
        long result = 0;
        for(int i = 0; i < Long.BYTES; ++i)
        {
            result <<= Byte.SIZE;
            result |= (bytes[i] & 0xFF);
        }
        return result;
    }

    public static byte[] longToByteList(long value)
    {
        byte[] result = new byte[Long.BYTES];
        for(int i = Long.BYTES - 1; i > -1; --i)
        {
            result[i] = (byte)(value & 0xFF);
            value >>= Byte.SIZE;
        }
        return result;
    }

    public static byte[] concatByteLists(byte[] first, byte[] second)
    {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }
}