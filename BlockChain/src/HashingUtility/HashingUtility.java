package HashingUtility;

//not sure for how long this class will be useful, if I will use it more than for debugging
//i create new file for it
public class HashingUtility {
    public static String byteListToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static long byteListToLong(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < Long.BYTES; ++i) {
            result <<= Byte.SIZE;
            result |= (bytes[i] & 0xFF);
        }
        return result;
    }

    public static byte[] longToByteList(long value) {
        byte[] result = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i > -1; --i) {
            result[i] = (byte) (value & 0xFF);
            value >>= Byte.SIZE;
        }
        return result;
    }

    private static byte[] concatTwoByteLists(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }

    public static byte[] concatByteLists(byte[]... args)
    {
        byte[] result = new byte[0];
        for(byte[] arg: args)
        {
            result = concatTwoByteLists(result, arg);
        }
        return result;
    }
}
