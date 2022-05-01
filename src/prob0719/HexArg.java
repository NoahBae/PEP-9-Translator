package prob0719;

public class HexArg extends AArg {
    private final int hexVal;
    public int getIntValue() {
        return hexVal;
    }
    public HexArg(int i) {
        hexVal = i;
    }
    public String generateListing() {
        return String.format("0x%04X", hexVal);
    }
    @Override
    public String generateCode() {
        if (hexVal >= 0) {
            int first = hexVal / 256;
            int second = hexVal % 256;
            return String.format("%02X %02X", first, second);
        } else {
            int neg = -hexVal;
            int out;
            int range = 32767;
            out = 2 * range - neg + 2;
            int first = out / 256;
            int second = out % 256;
            return String.format("%02X %02X", first, second);
        }
    }
}
