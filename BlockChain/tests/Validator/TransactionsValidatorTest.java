package Validator;

import BlockChain.BlockChain;
import Transaction.Transactions;
import org.junit.jupiter.api.BeforeEach;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsValidatorTest
{
    @BeforeEach
    public void setUp() throws NoSuchProviderException, NoSuchAlgorithmException
    {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");
        kpg.initialize(1024);
        //lets assume that first keys are belong to crypto owner - rest to the newcomers
        KeyPair[] keyPairs = new KeyPair[4];
        for (int i = 0; i < keyPairs.length; i++) keyPairs[i] = kpg.generateKeyPair();

    }
}