package Trees.MerkleTree;

import BlockChain.Blocks.Datable;
import BlockChain.StrData;//added only for debug purposes, remove with main
import HashingUtility.HashUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree
{
    private static byte[] getHash(ArrayList<byte[]> in)
    {
        try
        {
            if (in.size() >= 2)
            {
                return HashUtil.hash(
                        HashUtil.concatTwoByteLists(
                                in.remove(0),
                                in.remove(0)));
            }
            else if (in.size() == 1) { return HashUtil.hash(in.remove(0)); }
        }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    private static byte[] recursiveHashing(ArrayList<byte[]> in)
    {
        ArrayList<byte[]> out = new ArrayList<>();
        while(in.size() > 0) { out.add(getHash(in)); }
        if(out.size() == 1) return out.get(0);
        return recursiveHashing(out);
    }

    private static ArrayList<byte[]> getHashesArrayList(List<Datable> in)
    {
        ArrayList<byte[]> out = new ArrayList<>();
        for(Datable n: in) { out.add(n.getBytes()); }
        return out;
    }

    public static byte[] getMerkleRoot(List<Datable> in)
    {
        return recursiveHashing(getHashesArrayList(in));
    }

    public static void main(String[] args)
    {
        ArrayList<Datable> data = new ArrayList<>();
        data.add(new StrData("foo"));
        data.add(new StrData("bar"));
        data.add(new StrData("NICE"));
        data.add(new StrData("hello"));
        data.add(new StrData("world"));
        byte[] hash = MerkleTree.getMerkleRoot(data);
        System.out.println(HashUtil.byteListToString(hash));
    }
}
