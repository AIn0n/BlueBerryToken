package BlockChain.Blocks;

import java.util.Arrays;

import HashingUtility.HashUtil;

public class StdBlock extends Block
{
    private final byte[] prevHash;
    private long nonce = 0;

    public StdBlock(Hashable data, byte[] prevHash)
    {
        this.data = data;
        this.prevHash = prevHash;
    }

    private byte[] convertToBytes()
    {
        return HashUtil.concatByteLists(
            HashUtil.longToByteList(this.nonce),
            this.data.getHash(),
            this.prevHash
        );
    }

    public byte[] calculateHash()
    {
        return HashUtil.hash(this.convertToBytes());
    }

    @Override
    public boolean isHashValid() { return Arrays.equals(calculateHash(), this.hash); }

    public String toString()
    {
        return (
            "message: "     + this.data +
            "\nhash: "      + HashUtil.byteListToString(this.hash) +
            "\nprev hash: " + HashUtil.byteListToString(this.prevHash) + '\n');
    }

    public void setNonce(long nonce) {this.nonce = nonce;}

    public String getPrevHashAsString() { return HashUtil.byteListToString(this.prevHash);}
    public byte[] getPrevHash() { return this.prevHash;}
}