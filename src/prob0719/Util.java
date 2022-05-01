//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower dec
package prob0719;

public class Util {
    public boolean isValid(char ch) {
        boolean valid = isDigit(ch) || isAlpha(ch) || ch == ' ' || ch == '.' || ch ==
',' || sign(ch);
        return valid;
    }
    public static boolean sign(char ch) {
        return ch == '+' || ch == '-';
    }
    public static boolean isDigit(char ch) {
        return (('0' <= ch) && (ch <= '9'));
    }
    public static boolean isAlpha(char ch) {
        ch = Character.toLowerCase(ch);
        return (('a' <= ch) && (ch <= 'z'));
    }
    public static boolean isHexAlpha(char ch) {
        ch = Character.toLowerCase(ch);
        return (('a' <= ch) && (ch <= 'f'));
    }
    public static int decToVal(char hex) {
        hex = Character.toLowerCase(hex);
        int dec = 0;
        if (isHexAlpha(hex)) {
            dec = hex - 'a' + 10;
        } else if (isDigit(hex)) {
            dec = hex - '0';
        }
        return dec;
    }
    public static boolean branchMnemon(Mnemon mn) {
        if (mn == Mnemon.M_BR || mn == Mnemon.M_BRLT || mn == Mnemon.M_BREQ || mn ==
                Mnemon.M_BRLE) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean deciStwa(Mnemon m) {
        return m == Mnemon.M_DECI || m == Mnemon.M_STWA;
    }
}
