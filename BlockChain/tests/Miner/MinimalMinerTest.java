package Miner;

import BlockChain.BlockChain;
import BlockChain.Blocks.StdBlock;

import static org.junit.jupiter.api.Assertions.*;

import BlockChain.Blocks.Hashable;
import HashingUtility.HashUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimalMinerTest {

    @DisplayName("should mine block with proper hash")
    @Test
    public void shouldMineBlock()
    {
        BlockChain bc = new BlockChain(new StrData("foo"));
        StdBlock minedBlock = MinimalMiner.mine(new StrData("bar"), bc.last().getHash());
        assertEquals(0x77, minedBlock.getHash()[0]);
        assertEquals(bc.last().getHash(), minedBlock.getPrevHash());
    }
    //testing only class - not for production code
    static class StrData implements Hashable {

        private final String string;
        public StrData(String str) {
            this.string = str;
        }

        public String toString() {
            return this.string;
        }

        @Override
        public byte[] getHash() {
            return HashUtil.hash(this.string.getBytes());
        }
    }
}