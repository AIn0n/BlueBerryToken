package BlockChain.Blocks;

import java.security.PublicKey;
import java.util.Arrays;

import HashingUtility.HashUtil;

public class StdBlock extends Block
{
    private final byte[] prevHash;
    private final PublicKey miner;
    private long nonce = 0;
    private final Hashable data;

    public StdBlock(Hashable data, byte[] prevHash, PublicKey miner)
    {
        this.data = data;
        this.prevHash = prevHash;
        this.miner = miner;
    }

    private byte[] convertToBytes()
    {
        return HashUtil.concatByteLists(
            HashUtil.longToByteList(this.nonce),
            this.miner.getEncoded(),
            this.data.getHash(),
            this.prevHash
        );
    }

    public byte[] calculateHash()
    {
        return HashUtil.hash(this.convertToBytes());
    }

    @Override
    public boolean verify() { return Arrays.equals(calculateHash(), this.hash); }

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