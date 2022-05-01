package prob0719;
public class UnaryInstr extends ACode {
    private final Mnemon mnemonic;
    public UnaryInstr(Mnemon mn) {
        mnemonic = mn;
    }
    @Override
    public String generateListing() {
        return Maps.unaryMnemonStringTable.get(mnemonic) + "\n";
    }
    @Override
    public String generateCode() {
        switch (mnemonic) {
            case M_STOP:
            case M_ASRA:
            case M_ASLA:
                return String.format("%02X\n", Maps.unaryMnemonIntTable.get(mnemonic));
            default:
                return "";
        }
    }
}
