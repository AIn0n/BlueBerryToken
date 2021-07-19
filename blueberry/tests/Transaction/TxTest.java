package Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TxTest {
    public KeyPair[] keyPairs;
    public int numOfKeyPairs = 4;

    @BeforeEach
    public void setUp() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");
        kpg.initialize(1024);

        keyPairs = new KeyPair[numOfKeyPairs];
        for (int i = 0; i < keyPairs.length; i++)
            keyPairs[i] = kpg.generateKeyPair();
    }

    @DisplayName("test function isBalanceValid in proper transaction case")
    @Test
    public void checkBalanceValidationWithValidTx0()
    {

        Tx tx = new Tx(
                new HashSet<TxIn>() {{ add(new TxIn(new byte[1], 100));}},
                new HashSet<TxOut>() {{ add(new TxOut(keyPairs[0].getPublic(), 100, 0));}}
        );
        assertTrue(tx.isBalanceValid());
    }

    @DisplayName("test function isBalanceValid in proper transaction case with many ins/outs")
    @Test
    public void checkBalanceValidationWithValidTx1()
    {

        Tx tx = new Tx(
                new HashSet<TxIn>() {{
                    add(new TxIn(new byte[1], 100));
                    add(new TxIn(new byte[1], 300)); }},
                new HashSet<TxOut>() {{
                    add(new TxOut(keyPairs[0].getPublic(), 280, 0));
                    add(new TxOut(keyPairs[0].getPublic(), 120, 1));}}
        );
        assertTrue(tx.isBalanceValid());
    }

    @DisplayName("test function isBalanceValid in inproper transaction case")
    @Test
    public void checkBalanceValidationWithInvalidTx0()
    {
        Tx tx = new Tx(
                new HashSet<TxIn>() {{ add(new TxIn(new byte[1], 100));}},
                new HashSet<TxOut>() {{ add(new TxOut(keyPairs[0].getPublic(), 80, 0));}}
        );
        assertFalse(tx.isBalanceValid());
    }
}