package BlockChain;

import BlockChain.Blocks.Datable;

//testing only class - not for production code
public class StrData implements Datable {
    private final String string;

    public StrData(String str) {
        this.string = str;
    }

    @Override
    public byte[] getBytes() {
        return this.string.getBytes();
    }

    public String toString() {
        return this.string;
    }
}