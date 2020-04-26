package Transport4Future.TokenManagement.service;

/**
 * Type checking required to strict JSON Gson deserialization.
 */
public class TypeChecker {
    /**
     * Check if a given string could be a integer.
     *
     * @param input String to check.
     * @return If this is a valid number.
     */
    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
}
