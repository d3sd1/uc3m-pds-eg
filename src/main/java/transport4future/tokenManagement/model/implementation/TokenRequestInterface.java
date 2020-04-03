package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.TokenManagementException;

/**
 * The interface Token request interface.
 */
public interface TokenRequestInterface {
    /**
     * Request token string.
     *
     * @param inputFile the input file
     * @return the string
     * @throws TokenManagementException the token management exception
     */
    String RequestToken(String inputFile) throws TokenManagementException;
}
