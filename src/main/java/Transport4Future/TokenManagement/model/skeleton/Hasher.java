package Transport4Future.TokenManagement.model.skeleton;

import java.security.NoSuchAlgorithmException;

public interface Hasher {
    public byte[] encode(String toEncode) throws NoSuchAlgorithmException;
    public byte[] decode(String toDecode);
    public String getHex(byte[] encodedData);
}
