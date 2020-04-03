package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.TokenManagementException;

/**
 * The interface Token request generator interface.
 */
public interface TokenRequestGeneratorInterface {
    /**
     * Token request generation string.
     *
     * @param inputFile the input file
     * @return the string
     * @throws TokenManagementException the token management exception
     */
    String TokenRequestGeneration (String inputFile) throws TokenManagementException;
}
