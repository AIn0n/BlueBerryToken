package BlockChain;

import HashingUtility.HashingUtility;

import java.security.PublicKey;

public class Transaction implements Datable
{
    private final PublicKey from;
    private final PublicKey to;
    private final long amount;
    private final long index;

    Transaction(PublicKey from, PublicKey to, long amount, long idx)
    {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.index = idx;
    }

    @Override
    public byte[] getBytes() {
        byte[] result = HashingUtility.concatByteLists(from.getEncoded(), to.getEncoded());
        result = HashingUtility.concatByteLists(result, HashingUtility.longToByteList(amount));
        return HashingUtility.concatByteLists(result, HashingUtility.longToByteList(this.index));
    }
}
