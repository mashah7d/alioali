package ir.corestockexchanging.viewmodel;

/**
 * Created by MAShahsavand on 5/2/16 AD.
 */
public class DepositResponse {
    private long currentCredit;
    private boolean successful;

    public long getCurrentCredit() {
        return currentCredit;
    }

    public void setCurrentCredit(long currentCredit) {
        this.currentCredit = currentCredit;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
