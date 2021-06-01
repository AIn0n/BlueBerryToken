import java.util.ArrayList;

public class BlockChain
{
    ArrayList<Block> blocks;

    BlockChain(Datable initData)
    {
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block(initData, null));
    }

    void add(Block block)
    {
        byte[] lastBlockHash = this.blocks.get(this.blocks.size() - 1).getHash();
        block.setPrevHash(lastBlockHash);
        blocks.add(block);
    }
}