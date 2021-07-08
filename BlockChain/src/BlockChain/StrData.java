package BlockChain;

import BlockChain.Blocks.Hashable;
import HashingUtility.HashUtil;

//testing only class - not for production code
public class StrData implements Hashable {
    private final String string;

    public StrData(String str) {
        this.string = str;
    }
    public String toString() {
        return this.string;
    }

    @Override
    public byte[] getHash() {
        return HashUtil.hash(this.string.getBytes());
    }
}