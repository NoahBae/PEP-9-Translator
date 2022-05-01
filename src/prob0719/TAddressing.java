package prob0719;

public class TAddressing extends AToken {
    private final String addressing;
    public TAddressing(StringBuffer stringBuffer) {
        addressing = new String(stringBuffer);
    }
    @Override
    public String getDescription() {
        return String.format("Addressing Mode = %s", addressing);
    }
    public String getStringValue() {
        return addressing;
    }
}
