package BlockChain;

import BlockChain.Blocks.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BlockChain implements Iterable<Block>
{
    ArrayList<Block> blocks;

    public BlockChain(Datable initData)
    {
        this.blocks = new ArrayList<>();
        this.blocks.add(new GenesisBlock(initData));
    }

    @Override
    public Iterator<Block> iterator() { return blocks.iterator(); }
    public void add(StdBlock block) { blocks.add(block); }
    public Block last() { return this.blocks.get(this.blocks.size() - 1); }
}