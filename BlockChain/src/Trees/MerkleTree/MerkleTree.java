package Trees.MerkleTree;

import core.BlockChain.Blocks.Hashable;
import core.BlockChain.StrData;//added only for debug purposes, remove with main
import core.HashUtil;
import java.util.ArrayList;

public class MerkleTree
{
    private static byte[] getHash(ArrayList<byte[]> in)
    {
        return (in.size() >= 2) ?
            HashUtil.hash(
            HashUtil.concatTwoByteLists(
                in.remove(0),
                in.remove(0))) :
            HashUtil.hash(in.remove(0));
    }

    private static byte[] recursiveHashing(ArrayList<byte[]> in)
    {
        ArrayList<byte[]> out = new ArrayList<>();
        while(in.size() > 0) { out.add(getHash(in)); }
        if(out.size() == 1) return out.get(0);
        return recursiveHashing(out);
    }

    private static ArrayList<byte[]> getHashesArrayList(Iterable<? extends Hashable> in)
    {
        ArrayList<byte[]> out = new ArrayList<>();
        for(Hashable n: in) { out.add(n.getHash()); }
        return out;
    }

    public static byte[] getMerkleRoot(Iterable<? extends Hashable> in)
    {
        return recursiveHashing(getHashesArrayList(in));
    }

    public static void main(String[] args)
    {
        ArrayList<StrData> data = new ArrayList<>();
        data.add(new StrData("foo"));
        data.add(new StrData("bar"));
        data.add(new StrData("NICE"));
        data.add(new StrData("hello"));
        data.add(new StrData("world"));
        byte[] hash = MerkleTree.getMerkleRoot(data);
        System.out.println(HashUtil.byteListToString(hash));
    }
}
