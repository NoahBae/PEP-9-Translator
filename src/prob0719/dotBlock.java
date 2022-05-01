package prob0719;

public class dotBlock extends ACode{
    private final dotCommand dotBlock;
    private final AArg arg;
    public dotBlock(dotCommand block, AArg a){
        dotBlock = block;
        arg = a ;
    }
    @Override
    public String generateListing() {
        return String.format("%s %s",
                Maps.dotCommandStringTable.get(dotBlock),arg.generateListing()+"\n");
    }
    @Override
    public String generateCode() {
        for (int i = 0; i < arg.getIntValue(); i++) {
            System.out.print(String.format("%02d ", 00));
        }
        return "\n";
    }
}
