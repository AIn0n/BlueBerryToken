package BlockChain;

import BlockChain.Blocks.*;
import KeyTree.KeyTree;
import java.util.Iterator;

public class BlockChain implements Iterable<Block>
{
    KeyTree<Block> blocks;

    public BlockChain(Datable initData)
    {
        GenesisBlock genesis = new GenesisBlock(initData);
        this.blocks = new KeyTree<Block>(genesis, genesis.getHashAsString());
    }

    @Override
    public Iterator<Block> iterator() { return blocks.iterator(); }
    public void add(StdBlock block)
    {
        try { blocks.add(block, block.getHashAsString(), block.getPrevHashAsString()); }
        catch(Exception e) { e.printStackTrace(); }
    }
    public Block last() { return this.blocks.getLastData(); }
}