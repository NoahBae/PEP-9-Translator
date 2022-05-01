package prob0719;

public class Tokenizer {
    private final InBuffer b;
    public Tokenizer(InBuffer inBuffer) {
        b = inBuffer;
    }
    public AToken getToken() {
        char nextChar;
        StringBuffer localStringValue = new StringBuffer("");
        int localIntValue = 0;
        int sign = +1;
        AToken aToken = new TEmpty();
        LexState state = LexState.LS_START;
        do {
            nextChar = b.advanceInput();
            switch (state) {
                case LS_START:
                    if (Util.isDigit(nextChar)) {
                        if (nextChar == '0') {
                            state = LexState.LS_INT1;
                        } else {
                            state = LexState.LS_INT2;
                        }
                        localIntValue = nextChar - '0';
                    } else if (Util.sign(nextChar)) {
                        state = LexState.LS_SIGN;
                        if (nextChar == '-') {
                            sign = -1;
                        }
                    } else if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                        state = LexState.LS_IDENT;
                    } else if (nextChar == '.') {
                    localStringValue.append(nextChar);
                    state = LexState.LS_DOT1;
                } else if (nextChar == ',') {
                    state = LexState.LS_ADDR1;
                } else if (nextChar == '\n') {
                    state = LexState.LS_STOP;
                } else if (nextChar == ' ') {
                    state = LexState.LS_START;
                } else if (nextChar != ' ') {
                    aToken = new TInvalid();
                }
                break;
                case LS_INT1:
                    if (nextChar == 'x' || nextChar == 'X') {
                    state = LexState.LS_HEX1;
                } else if (Util.isDigit(nextChar)) {
                    state = LexState.LS_INT2;
                    localIntValue = 10 * localIntValue + Util.decToVal(nextChar);
                } else {
                    b.backUpInput();
                    aToken = new TInteger(sign * localIntValue);
                    state = LexState.LS_STOP;
                }
                break;
                case LS_INT2:
                    if (Util.isDigit(nextChar)) {
                        if (sign == 1) {
                            if (10 * localIntValue + Util.decToVal(nextChar) > 65535) {
                                aToken = new TInvalid();
                            } else {
                                localIntValue = 10 * localIntValue +
                                        Util.decToVal(nextChar);
                            }
                        } else if (10 * localIntValue + Util.decToVal(nextChar) > 32768)
                        {
                            aToken = new TInvalid();
                        } else {
                            localIntValue = 10 * localIntValue + Util.decToVal(nextChar);
                        }
                    } else {
                        b.backUpInput();
                        aToken = new TInteger(sign * localIntValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_HEX1:
                    if (Util.isHexAlpha(nextChar) || Util.isDigit(nextChar)) {
                        localIntValue = Util.decToVal(nextChar);
                        state = LexState.LS_HEX2;
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_HEX2:
                    if (Util.isHexAlpha(nextChar) || Util.isDigit(nextChar)) {
                        if (16 * localIntValue + Util.decToVal(nextChar) > 65535) {
                            aToken = new TInvalid();
                        }
                        localIntValue = 16 * localIntValue + Util.decToVal(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new THexDigit(localIntValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_SIGN:
                    if (Util.isDigit(nextChar)) {
                        localIntValue = nextChar - '0';
                        state = LexState.LS_INT2;
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_IDENT:
                    if (Util.isDigit(nextChar) || Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TIdentifier(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_DOT1:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                        state = LexState.LS_DOT2;
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_DOT2:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TDot(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_ADDR1:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                        state = LexState.LS_ADDR2;
                    } else if (nextChar == ' ') {
                    state = state;
                } else {
                    aToken = new TInvalid();
                }
                break;
                case LS_ADDR2:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TAddressing(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
            }
        } while ((state != LexState.LS_STOP) && !(aToken instanceof TInvalid));
        return aToken;
    }
}
