package prob0719;

public class dotEnd extends ACode{
    private final dotCommand dot;
    public dotEnd(dotCommand dc) {
        dot = dc;
    }
    @Override
    public String generateListing() {
        return Maps.dotCommandStringTable.get(dot) + "\n";
    }
    @Override
    public String generateCode() {
        return String.format("zz\n");
    }
}
