package BlockChain;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockChain implements Iterable<Block>
{
    ArrayList<Block> blocks;

    public BlockChain(Datable initData)
    {
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block(initData));
    }

    @Override
    public Iterator<Block> iterator() { return blocks.iterator(); }

    public void add(Block block) { blocks.add(block); }

    public Block last() { return this.blocks.get(this.blocks.size() - 1); }

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);
        bc.add(new Block(new StrData("bar")));

        for (Block block : bc) { block.printBlock(); }
    }
}

