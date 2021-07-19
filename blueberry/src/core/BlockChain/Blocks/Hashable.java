package core.BlockChain.Blocks;

import java.io.Serializable;

public interface Hashable extends Serializable
{
    byte[] getHash();
}