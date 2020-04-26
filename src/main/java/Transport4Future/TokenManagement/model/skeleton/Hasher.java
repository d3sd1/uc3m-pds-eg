package Transport4Future.TokenManagement.model.skeleton;

import java.security.NoSuchAlgorithmException;

/**
 *
 */
public interface Hasher {
    /**
     *
     * @param toEncode
     * @return
     * @throws NoSuchAlgorithmException
     */
    public byte[] encode(String toEncode) throws NoSuchAlgorithmException;

    /**
     *
     * @param encodedData
     * @return
     */
    public String hex(byte[] encodedData);
}
