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

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);

    }
}

class StrData implements Datable
{
    private final String string;

    StrData(String str) { this.string = str; }

    @Override
    public byte[] getBytes() { return this.string.getBytes(); }
}