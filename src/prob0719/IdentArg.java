package prob0719;

public abstract class IdentArg extends AArg{
    private final String identValue;
    public IdentArg(String str) {
        identValue = str;
    }
    @Override
    public String generateCode() {
        return identValue;
    }
}
