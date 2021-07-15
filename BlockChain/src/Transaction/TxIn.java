package Transaction;

import BlockChain.Blocks.Hashable;
import HashingUtility.HashUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class TxIn implements Hashable {
    private final byte[] prevOutHash;
    private final byte[] hash;
    private byte[] signature;
    private final long amount;

    public TxIn(byte[] prevOutHash, long amount) {
        this.prevOutHash = prevOutHash;
        this.amount = amount;
        this.hash = calculateHash();
    }
    public byte[] getHash() {
        return this.hash;
    }
    public byte[] calculateHash() {
        return HashUtil.hash(getBytes());
    }

    public byte[] getPrevOutHash() { return this.prevOutHash;}

    public byte[] getBytes() {
        return HashUtil.concatTwoByteLists(
                HashUtil.longToByteList(amount),
                this.prevOutHash
        );
    }

    public boolean verifySignature(PublicKey pk) {
        try
        {
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initVerify(pk);
            dsa.update(HashUtil.concatTwoByteLists(getBytes(), hash));
            return dsa.verify(this.signature);
        }
        catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public void sign(PrivateKey sk) {
        try {
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(sk);
            dsa.update(HashUtil.concatTwoByteLists(getBytes(), hash));
            this.signature = dsa.sign();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public long getAmount() { return amount; }
}