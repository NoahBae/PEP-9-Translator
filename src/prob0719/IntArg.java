package prob0719;

public class IntArg extends AArg{
    private final int intValue;
    public int getIntValue() {

        return intValue;
    }
    public IntArg(int i) {

        intValue = i;
    }
    public String generateListing() {

        return String.format("%d", intValue);
    }
    @Override
    public String generateCode() {
        if (intValue >= 0) {
            int first = intValue / 256;
            int second = intValue % 256;
            return String.format("%02X %02X", first & 0xFF, second & 0xFF);
        } else {
            int neg = -intValue;
            int out;
            int range = 32767;
            out = 2 * range - neg + 2;
            int first = out / 256;
            int second = out % 256;
            return String.format("%02X %02X", first, second);
        }
    }
}
