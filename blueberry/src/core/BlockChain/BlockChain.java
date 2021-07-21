package core.BlockChain;

import core.BlockChain.Blocks.*;
import Trees.KeyTree.KeyTree;
import java.util.Iterator;

public class BlockChain implements Iterable<Block>
{
    KeyTree<Block, String> blocks;

    public BlockChain(Hashable initData)
    {
        GenesisBlock genesis = new GenesisBlock(initData);
        this.blocks = new KeyTree<>(genesis, genesis.getHashAsString());
    }

    @Override
    public Iterator<Block> iterator() { return blocks.iterator(); }
    public void add(StdBlock block)
    {
        try { blocks.add(block, block.getHashAsString(), block.getPrevHashAsString()); }
        catch(Exception e) { e.printStackTrace(); }
    }
    public Block last() { return this.blocks.getLastData(); }
    public Block first() { return this.blocks.getRootData(); }
}