package Transaction;

import BlockChain.Blocks.Hashable;
import HashingUtility.HashUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;

class TxIn implements Hashable {
    private final byte[] prevOutHash;
    private final byte[] hash;
    private byte[] signature;
    private final long amount;

    public TxIn(byte[] prevOutHash, long amount) {
        this.prevOutHash = prevOutHash;
        this.amount = amount;
        this.hash = getHash();
    }

    public byte[] getHash() {
        return HashUtil.hash(getBytes());
    }

    public byte[] getBytes() {
        return HashUtil.concatTwoByteLists(
                HashUtil.longToByteList(amount),
                this.prevOutHash
        );
    }

    private boolean verifySignature(PublicKey pk) {
        try {
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initVerify(pk);
            dsa.update(getBytes());
            return dsa.verify(this.signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verify(PublicKey pk, Iterable<? extends Tx> transactions) {
        for (Tx n : transactions) {
            Iterable<TxOut> outs = n.getOutputTx();
            for (TxOut m : outs) {
                if (Arrays.equals(m.getHash(), this.prevOutHash))
                    return verifySignature(m.getRecipient());
            }
        }
        return false;
    }

    public void sign(PrivateKey sk) {
        try {
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(sk);
            dsa.update(HashUtil.concatTwoByteLists(getBytes(), hash));
            this.signature = dsa.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getAmount() { return amount; }
}
