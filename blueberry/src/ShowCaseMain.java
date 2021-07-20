import InitBlueBerryToken.BlueBerryInit;
import core.BlockChain.BlockChain;

import java.security.*;
import java.util.*;

public class ShowCaseMain
{
    public static void printKeyPair(KeyPair kp)
    {
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.print("pk: " + encoder.encodeToString(kp.getPrivate().getEncoded()).substring(255, 265) + '\t');
        System.out.println("sk: " + encoder.encodeToString(kp.getPublic().getEncoded()).substring(255, 265));
    }

    public static void main(String[]a)
    {
        System.out.println("Please insert number of public/secret key pairs to generate");
        Scanner input = new Scanner(System.in);
        try
        {
            ArrayList<KeyPair> keys = BlueBerryInit.generateKeyPairs(input.nextInt() + 1);
            KeyPair owner = keys.remove(0);
            for(KeyPair kp: keys) printKeyPair(kp);
            BlockChain bc = BlueBerryInit.generateBlockChain(owner, keys, 1000);
            System.out.println("Blockchain generated");


        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) { e.printStackTrace(); }

    }
}
