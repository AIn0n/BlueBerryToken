package Trees.MerkleTree;

import BlockChain.Blocks.Datable;
import BlockChain.StrData;//added only for debug purposes, remove with main
import HashingUtility.HashUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree
{
    private ArrayList<byte[]> list = new ArrayList<>();

    public MerkleTree(List<Datable> inList)
    {
        for(Datable n: inList) { list.add(n.getBytes()); }
    }

    private byte[] getHash()
    {
        try
        {
            if (this.list.size() >= 2)
            {
                return HashUtil.hash(
                        HashUtil.concatTwoByteLists(
                                this.list.remove(0),
                                this.list.remove(0)));
            }
            else if (this.list.size() == 1) { return HashUtil.hash(this.list.remove(0)); }
        }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    private void recursiveHashing()
    {
        ArrayList<byte[]> out = new ArrayList<>();
        while(list.size() > 0) { out.add(getHash()); }
        this.list = out;
        if(list.size() == 1) return;
        recursiveHashing();
    }

    public byte[] getMerkleRoot()
    {
        recursiveHashing();
        return list.get(0);
    }

    public static void main(String[] args)
    {
        ArrayList<Datable> data = new ArrayList<>();
        data.add(new StrData("foo"));
        data.add(new StrData("bar"));
        data.add(new StrData("NICE"));
        data.add(new StrData("hello"));
        data.add(new StrData("world"));
        MerkleTree merkleTree = new MerkleTree(data);
        System.out.println(HashUtil.byteListToString(merkleTree.getMerkleRoot()));
    }
}
