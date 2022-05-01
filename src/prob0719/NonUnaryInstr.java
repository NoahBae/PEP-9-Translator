package prob0719;

public class NonUnaryInstr extends ACode{
    private final Mnemon mnemonic;
    private final AArg Arg;
    private final addressingMode Address;
    public NonUnaryInstr(Mnemon mn, AArg arg, addressingMode ad) {
        mnemonic = mn;
        Arg = arg;
        Address = ad;
    }
    @Override
    public String generateListing() {
        if (mnemonic == Mnemon.M_BR || mnemonic == Mnemon.M_BRLE || mnemonic ==
                Mnemon.M_BREQ || mnemonic == Mnemon.M_BRLT) {
            if (mnemonic == Mnemon.M_BR) {
                return String.format("%s %s%s",
                        Maps.mnemonStringTable.get(mnemonic), Arg.generateListing(),
                        Maps.addressingModeStringTable.get(Address) + "\n");
            } else {
                return String.format("%s %s%s",
                        Maps.mnemonStringTable.get(mnemonic), Arg.generateListing(),
                        Maps.addressingModeStringTable.get(Address) + "\n");
            }
        } else {
            return String.format("%s %s,%s", Maps.mnemonStringTable.get(mnemonic),
                    Arg.generateListing(), Maps.addressingModeStringTable.get(Address) + "\n");
        }
    }
    @Override
    public String generateCode() {
        if (Util.branchMnemon(mnemonic)) {
            int ordinal = 0;
            if (Address == addressingMode.A_I) {
                ordinal = 0;
            } else if (Address == addressingMode.A_X) {
                ordinal = 1;
            }
            return String.format("%02X %s\n", Maps.mnemonIntTable.get(mnemonic) +
                    ordinal, Arg.generateCode());
        } else {
            return String.format("%02X %s\n", Maps.mnemonIntTable.get(mnemonic) +
                    Address.ordinal(), Arg.generateCode());
        }
    }
}
