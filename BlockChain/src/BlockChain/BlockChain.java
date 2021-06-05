package BlockChain;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockChain implements Iterable<Block>
{
    ArrayList<Block> blocks;

    BlockChain(Datable initData)
    {
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block(initData, null));
    }

    @Override
    public Iterator<Block> iterator() { return blocks.iterator(); }

    void add(Block block)
    {
        Block lastBlock = this.blocks.get(this.blocks.size() - 1);
        block.setPrevHash(lastBlock.getHash());
        block.setIndex(lastBlock.getIndex() + 1);
        blocks.add(block);
    }

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);
        bc.add(new Block(new StrData("bar")));

        for (Block block : bc) { block.printBlock(); }
    }
}

//testing only class - not for production code
class StrData implements Datable
{
    private final String string;

    StrData(String str) { this.string = str; }

    @Override
    public byte[] getBytes() { return this.string.getBytes(); }

    public String toString() { return this.string; }
}