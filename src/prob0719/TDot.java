package prob0719;

public class TDot extends AToken {
    private final String dotValue;
    public TDot(StringBuffer stringBuffer) {
        dotValue = new String(stringBuffer);
    }
    @Override
    public String getDescription() {
        return String.format("Dot command = %s", dotValue);
    }
    public String getStringValue() {
        return dotValue;
    }
}
