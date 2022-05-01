package prob0719;

public class TInteger extends AToken {
    private final int intValue;
    public TInteger(int i) {
        intValue = i;
    }
    @Override
    public String getDescription() {
        return String.format("Integer = %d", intValue);
    }
    public int getIntValue() {
        return intValue;
    }
}
