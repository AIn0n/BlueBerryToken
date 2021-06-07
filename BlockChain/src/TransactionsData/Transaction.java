package TransactionsData;

import BlockChain.Datable;
import HashingUtility.HashingUtility;

import java.security.*;

public class Transaction implements Datable
{
    private final PublicKey from;
    private final PublicKey to;
    private byte[] signature;
    private final long amount;
    private final long tip;
    private final long index;

    Transaction(KeyPair senderKeys, PublicKey to, long amount, long idx, long tip)
    {
        this.from = senderKeys.getPublic();
        this.to = to;
        this.amount = amount;
        this.tip = tip;
        this.index = idx;
        sign(senderKeys.getPrivate());
    }

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
            dsa.initVerify(this.from);
            dsa.update(getBytes());
            return dsa.verify(this.signature);
        }
        catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public String toString()
    {
        return "from: " + this.from + " to: " + this.to + " amount: " + this.amount + " tip: " + this.tip;
    }

    @Override
    public byte[] getBytes()
    {
        return HashingUtility.concatByteLists(
            this.from.getEncoded(),
            this.to.getEncoded(),
            HashingUtility.longToByteList(this.amount),
            HashingUtility.longToByteList(this.tip),
            HashingUtility.longToByteList(this.index));
    }

    public byte[] getSignature() { return signature; }

    public static void main(String[] args)
    {
        try
        {
            KeyPairGenerator KeyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            KeyGen.initialize(1024);
            KeyPair pair = KeyGen.genKeyPair();
            Transaction transaction = new Transaction(pair, pair.getPublic(), 1000, 1, 2);
            System.out.println("verification > " + transaction.verify());
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) { e.printStackTrace(); }
    }
}
