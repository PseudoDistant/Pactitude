package dev.pseudodistant.pactitude;

public class PacManager {
    public enum PacStatus {
        INSTALL,
        REMOVE,
        QUERY
    }

    PacStatus status;
    String[] packageList;

    boolean isLocal = false;
    boolean isSearch = false;
    boolean isSync = false;
    boolean isUpgrade = false;

    public PacManager(String[] packages) {
        this.packageList = packages;
    }

    public void setStatus(PacStatus stat) {
        this.status = stat;
    }

    public void doLocal() {
        this.isLocal = true;
    }

    public void doSearch() {
        this.isSearch = true;
    }

    public void doUpgrade() {
        isUpgrade = true;
    }

    public void doSync() {
        isSync = true;
    }

    public void installPackages() {

    }
}
