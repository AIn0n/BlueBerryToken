package Transaction;

import BlockChain.Blocks.Hashable;
import HashingUtility.HashUtil;

import java.security.PublicKey;

public class TxOut implements Hashable {
    private final PublicKey recipient;
    private final long amount;
    private final long index;
    private final byte[] hash;

    public TxOut(PublicKey recipient, long amount, long index) {
        this.recipient = recipient;
        this.amount = amount;
        this.index = index;
        this.hash = calculateHash();
    }

    public byte[] getBytes() {
        return HashUtil.concatByteLists(
                HashUtil.longToByteList(this.amount),
                HashUtil.longToByteList(this.index),
                this.recipient.getEncoded()
        );
    }

    public byte[] calculateHash() { return HashUtil.hash(getBytes()); }
    public byte[] getHash() { return hash; }
    public PublicKey getRecipient() { return recipient; }
    public long getAmount() { return amount; }
}
