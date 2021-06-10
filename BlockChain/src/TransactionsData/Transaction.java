package TransactionsData;

import BlockChain.Blocks.Datable;
import HashingUtility.HashUtil;
import java.security.*;
import java.util.Arrays;

public class Transaction implements Datable
{
    private final PublicKey sender;
    private final PublicKey recipient;
    private byte[] signature;
    private final long amount;
    private final long tip;
    private final long index;

    Transaction(KeyPair senderKeys, PublicKey recipient, long amount, long idx, long tip)
    {
        this.sender = senderKeys.getPublic();
        this.recipient = recipient;
        this.amount = amount;
        this.tip = tip;
        this.index = idx;
        sign(senderKeys.getPrivate());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Arrays.equals(this.getBytes(), that.getBytes());
    }
    @Override
    public int hashCode() { return HashUtil.byteListToInt(HashUtil.hash(this.getBytes())); }

    private void sign(PrivateKey sk)
    {
        try
        {
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(sk);
            dsa.update(getBytes());
            this.signature = dsa.sign();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public boolean verify()
    {
        try
        {
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initVerify(this.sender);
            dsa.update(getBytes());
            return dsa.verify(this.signature) && !(this.sender.equals(this.recipient));
        }
        catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public String toString()
    {
        return "from: " + this.sender.hashCode() + " to: " + this.recipient.hashCode() + " amount: " + this.amount + " tip: " + this.tip;
    }

    @Override
    public byte[] getBytes()
    {
        return HashUtil.concatByteLists(
            this.sender.getEncoded(),
            this.recipient.getEncoded(),
            HashUtil.longToByteList(this.amount),
            HashUtil.longToByteList(this.tip),
            HashUtil.longToByteList(this.index));
    }

    public PublicKey getSender() { return this.sender; }
    public PublicKey getRecipient() { return this.recipient; }
    public long getAmount() { return this.amount; }
}