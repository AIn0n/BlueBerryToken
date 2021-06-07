package BlockChain;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockChain implements Iterable<Block>
{
    ArrayList<Block> blocks;

    public BlockChain(Datable initData)
    {
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block(initData, null, 0));
    }

    @Override
    public Iterator<Block> iterator() { return blocks.iterator(); }

    public void add(Block block)
    {
        Block lastBlock = this.getLastBlock();
        block.setPrevHash(lastBlock.getHash());
        block.setIndex(lastBlock.getIndex() + 1);
        blocks.add(block);
    }

    private Block getLastBlock() { return this.blocks.get(this.blocks.size() - 1); }

    public byte[] getLastHash() { return this.getLastBlock().getHash(); }
    public long getLastIndex() { return this.getLastBlock().getIndex(); }

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);
        bc.add(new Block(new StrData("bar")));

        for (Block block : bc) { block.printBlock(); }
    }
}

