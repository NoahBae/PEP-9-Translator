package prob0719;

public class TIdentifier extends AToken {
    private final String stringValue;
    public TIdentifier(StringBuffer stringBuffer) {
        stringValue = new String(stringBuffer);
    }
    @Override
    public String getDescription() {
        return String.format("Identifier = %s\n", stringValue);
    }
    public String getStringValue() {
        return stringValue;
    }
}
