package prob0719;

public class End extends ACode{
    private final Mnemon mnemonic;

    public End(Mnemon mn) {

        this.mnemonic = mn;
    }
    @Override
    public String generateListing() {

        return (String)Maps.mnemonStringTable.get(this.mnemonic) + "\n";
    }
    @Override
    public String generateCode() {

        return "";
    }
}
