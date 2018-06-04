package pl.aitwar.aitcoin.dictionary;

public enum ExitCodes {
    NORMAL_EXIT(0), DB_CONNECTION_FAILED(1);

    private int numVal;

    ExitCodes(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
