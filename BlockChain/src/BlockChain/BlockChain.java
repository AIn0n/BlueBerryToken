package BlockChain;

import BlockChain.Blocks.StdBlock;
import BlockChain.Blocks.Datable;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockChain implements Iterable<StdBlock>
{
    ArrayList<StdBlock> blocks;

    public BlockChain(Datable initData)
    {
        this.blocks = new ArrayList<>();
        this.blocks.add(new StdBlock(initData));
    }

    @Override
    public Iterator<StdBlock> iterator() { return blocks.iterator(); }

    public void add(StdBlock block) { blocks.add(block); }

    public StdBlock last() { return this.blocks.get(this.blocks.size() - 1); }

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);
        bc.add(new StdBlock(new StrData("bar")));

        for (StdBlock block : bc) { block.printBlock(); }
    }
}