package netmessage;

/**
 * A class for NetMessage Utilities. 
 * 
 * @author isaac
 */
public class NetMessageUtils {
    
    /**
     * Formats a message to send over the Network
     * @param type Type of message, should almost always be 1.
     * @param message The actual message to print.
     * @return A formatted String.
     */
    public static String formatMessage(byte type, String message) {
        return Integer.toString(type) + ";" + message;
    }
}
