package prob0719;

public class THexDigit extends AToken {
    private final int hexValue;
    public THexDigit(int i) {
        hexValue = i;
    }
    @Override
    public String getDescription() {
        return String.format("Hexadecimal constant = %d", hexValue);
    }
    public int getIntValue() {
        return hexValue;
    }
}
