package prob0719;
import java.util.ArrayList;
import java.util.Iterator;

public class Translator {
    private final InBuffer b;
    private Tokenizer t;
    private ACode aCode;
    public Translator(InBuffer inBuffer) {
        b = inBuffer;
    }
    // Sets aCode and returns boolean true if end statement is processed.
    private boolean parseLine() {
        boolean terminate = false;
        AArg localArg = new IntArg(0);
        Mnemon localMnemon = Mnemon.M_BR; // Compiler requires useless initialization.
        dotCommand localDot = dotCommand.DC_BLOCK;
        addressingMode localAddress = addressingMode.A_I;
        AToken aToken;
        aCode = new EmptyInstr();
        ParseState state = ParseState.PS_START;
        do {
            aToken = t.getToken();
            switch (state) {
                case PS_START:
                    if (aToken instanceof TIdentifier) {
                        TIdentifier localTIdentifier = (TIdentifier) aToken;
                        String tempStr = localTIdentifier.getStringValue();
                        if (Maps.unaryMnemonTable.containsKey(tempStr.toUpperCase())) {
                            localMnemon =
                                    Maps.unaryMnemonTable.get(tempStr.toUpperCase());
                            aCode = new UnaryInstr(localMnemon);
                            state = ParseState.PS_UNARY;
                        } else if
                        (Maps.nonUnaryMnemonTable.containsKey(tempStr.toUpperCase())) {
                            localMnemon =
                                    Maps.nonUnaryMnemonTable.get(tempStr.toUpperCase());
                            state = ParseState.PS_INST_SPEC;
                        } else {
                            aCode = new Error("Illegal mnemonic");
                        }
                    } else if (aToken instanceof TDot) {
                        TDot localTDot = (TDot) aToken;
                        String tempStr = localTDot.getStringValue();
                        if (Maps.dotCommandTable1.containsKey(tempStr.toUpperCase())) {
                            localDot = Maps.dotCommandTable1.get(tempStr.toUpperCase());
                            state = ParseState.PS_PSEUDO_OP1;
                        } else if
                        (Maps.dotCommandTable2.containsKey(tempStr.toUpperCase())) {
                            localDot = Maps.dotCommandTable2.get(tempStr.toUpperCase());
                            terminate = localDot == dotCommand.DC_END;
                            state = ParseState.PS_PSEUDO_OP2;
                        } else {
                            aCode = new Error("Illegal Dot Command");
                        }
                    } else if (aToken instanceof TEmpty) {
                        aCode = new EmptyInstr();
                        state = ParseState.PS_FINISH;
                    } else {
                        aCode = new Error("Line must begin with an Insruction or Dot Command");
                    }
                    break;
                case PS_UNARY:
                    if (aToken instanceof TEmpty) {
                        state = ParseState.PS_FINISH;
                    } else {
                        aCode = new Error("Illegal next character");
                    }
                    break;
                case PS_INST_SPEC:
                    if (aToken instanceof TInteger || aToken instanceof THexDigit) {
                        if (aToken instanceof TInteger) {
                            TInteger localInt = (TInteger) aToken;
                            localArg = new IntArg(localInt.getIntValue());
                        } else if (aToken instanceof THexDigit) {
                            THexDigit localHex = (THexDigit) aToken;
                            localArg = new HexArg(localHex.getIntValue());
                        }
                        state = ParseState.PS_OP_SPEC;
                    } else {
                        aCode = new Error("Non-Unary Instructions must be followed by a constant");
                    }
                    break;
                case PS_PSEUDO_OP1:
                    if (aToken instanceof THexDigit || aToken instanceof TInteger) {
                        if (aToken instanceof TInteger) {
                            TInteger localInt = (TInteger) aToken;
                            int i = localInt.getIntValue();
                            if (i < 0) {
                                aCode = new Error(".BLOCK must be followed by a postive number");
                            } else {
                                localArg = new IntArg(localInt.getIntValue());
                            }
                        } else if (aToken instanceof THexDigit) {
                            THexDigit localHex = (THexDigit) aToken;
                            localArg = new HexArg(localHex.getIntValue());
                        }
                        state = ParseState.PS_MEM_ALOC;
                    } else {
                        aCode = new Error("Illegal hex constant");
                        }

                    break;
                case PS_PSEUDO_OP2:
                    if (aToken instanceof TEmpty) {
                        aCode = new dotEnd(localDot);
                        state = ParseState.PS_FINISH;
                    } else {
                        aCode = new Error("Illegal next character");
                    }
                    break;
                case PS_MEM_ALOC:
                    if (aToken instanceof TEmpty) {
                        aCode = new dotBlock(localDot, localArg);
                        state = ParseState.PS_FINISH;
                    } else {
                        aCode = new Error("Illegal next character");
                    }
                    break;
                case PS_OP_SPEC:
                    if (aToken instanceof TEmpty) {
                        if ((Util.branchMnemon(localMnemon))) {
                            localAddress = Maps.addressingModeTable.get("");
                            aCode = new NonUnaryInstr(localMnemon, localArg,
                                    localAddress);
                            state = ParseState.PS_FINISH;
                        } else {
                            aCode = new Error("Addressing Mode required for mnemonic");
                        }
                    } else if (aToken instanceof TAddressing) {
                        TAddressing localTAddress = (TAddressing) aToken;
                        String tempStr = localTAddress.getStringValue();
                        localAddress =
                                Maps.addressingModeTable.get(tempStr.toLowerCase());
                        if ((Util.branchMnemon(localMnemon)) && localAddress ==
                                addressingMode.A_I) {
                            localAddress = Maps.addressingModeTable.get("");
                            aCode = new NonUnaryInstr(localMnemon, localArg,
                                    localAddress);
                            state = ParseState.PS_FINISH;
                        } else if (Util.deciStwa(localMnemon) && localAddress !=
                                addressingMode.A_I) {
                            aCode = new NonUnaryInstr(localMnemon, localArg,
                                    localAddress);
                            state = ParseState.PS_NONUNARY;
                        } else if (!(Util.branchMnemon(localMnemon)) &&
                                !(Util.deciStwa(localMnemon)) &&
                                Maps.addressingModeTable.containsKey(tempStr.toLowerCase())) {
                            aCode = new NonUnaryInstr(localMnemon, localArg,
                                    localAddress);
                            state = ParseState.PS_NONUNARY;
                        } else if (Util.branchMnemon(localMnemon) && (localAddress ==
                                addressingMode.A_X)) {
                            aCode = new NonUnaryInstr(localMnemon, localArg,
                                    localAddress);
                            state = ParseState.PS_NONUNARY;
                        } else {
                            aCode = new Error("Illegal addressing mode");
                        }
                    } else {
                        aCode = new Error("Invalid mnemonic");
                    }
                    break;
                case PS_NONUNARY:
                    if (aToken instanceof TEmpty) {
                        aCode = new NonUnaryInstr(localMnemon, localArg, localAddress);
                        state = ParseState.PS_FINISH;
                    } else {
                        aCode = new Error("Illegal");
                    }
                    break;
            }
        } while (state != ParseState.PS_FINISH && !(aCode instanceof Error));
        return terminate;
    }
    public void translate() {
        ArrayList<ACode> codeTable = new ArrayList<>();
        int numErrors = 0;
        t = new Tokenizer(b);
        boolean terminateWithEnd = false;
        b.getLine();
        while (b.inputRemains() && !terminateWithEnd) {
            terminateWithEnd = parseLine(); // Sets aCode and returns boolean.
            codeTable.add(aCode);
            if (aCode instanceof Error) {
                numErrors++;
            }
            b.getLine();
        }
        if (!terminateWithEnd) {
            aCode = new Error("Missing .END sentinel");
            codeTable.add(aCode);
            numErrors++;
        }
        if (numErrors == 0) {
            System.out.printf("Object Code:\n");
            for (int i = 0; i < codeTable.size(); i++) {
                System.out.printf("%s", codeTable.get(i).generateCode());
            }
        }
        if (numErrors == 1) {
            System.out.printf("One error was detected.\n");
        } else if (numErrors > 1) {
            System.out.printf("%d errors were detected.\n", numErrors);
        }
        System.out.printf("\nProgram listing:\n");
        for (int i = 0; i < codeTable.size(); i++) {
            System.out.printf("%s", codeTable.get(i).generateListing());
        }
    }
}