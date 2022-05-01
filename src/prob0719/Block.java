package prob0719;

public class Block extends ACode{
    private final Mnemon mnemonic;

    public Block(Mnemon mn) {

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
