package netmessage;

/**
 * Holds translation for the ANSI CSI SGR codes.
 * 
 * @author isaac
 */

/*
Note to self on Escape codes:

ASCII characters 0-31 are control characters.
Control Character #27 (^[) is the Escape Character that signals what follows is not normal text
ANSI CSI sequences usally follow ^[. 
SGR is a CSI sequence defined as (^[#n, where # is a number).
*/
public enum AsciiSGR {
    NORM("^[0m"),
    BOLD("^[1m"),
    FAINT("^[2m"),
    ITALIC("^[3m"),
    UNDERLINE("^[4m"),
    //TODO: Support SGR codes 5-29
    
    /* Forground Color Escapes */
    BLACK("^[30m"),
    RED("^[31m"),
    GREEN("^[32m"),
    YELLOW("^[33m"),
    BLUE ("^[34m"),
    MAGENTA("^[35m"),
    CYAN("^[36m"),
    GREY("^[37m"),
    FCOLOR("^[38m"), //Xterm 256 colors(Foreground), not supported yet
    F_DEFALT("^[39m"), //Defult foreground color, should be Color.GREY
    /* Background Color Escapes */
    B_BLACK("^[40m"),
    B_RED("^[41m"),
    B_GREEN("^[42m"),
    B_YELLOW("^[43m"),
    B_BLUE ("^[44m"),
    B_MAGENTA("^[45m"),
    B_CYAN("^[46m"),
    B_GREY("^[47m"),
    B_FCOLOR("^[48m"), //Xterm 256 colors(Background), not supported yet
    B_DEFALT("^[49m"), //Defult background color, should be Color.BLACK
    /* Forground High-Intesity Color Escapes */
    I_BLACK("^[90m"),
    I_RED("^[91m"),
    I_GREEN("^[92m"),
    I_YELLOW("^[93m"),
    I_BLUE ("^[94m"),
    I_MAGENTA("^[95m"),
    I_CYAN("^[96m"),
    I_GREY("^[97m"),
    //98 is the same as 38, skipped
    //99 is the same as 39, skipped
    /* Background High-Intesity Color Escapes */
    IB_BLACK("^[100m"),
    IB_RED("^[101m"),
    IB_GREEN("^[102m"),
    IB_YELLOW("^[103m"),
    IB_BLUE ("^[104m"),
    IB_MAGENTA("^[105m"),
    IB_CYAN("^[106m"),
    IB_GREY("^[107m");
    //108 is the same as 48, skipped
    //108 is the same as 49, skipped
    
    private AsciiSGR(final String text) {
        this.text = text;
    }
    
    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
