package uz.ccrew.matchmaking.enums;

public enum MatchMode {
    TDM(2), FFA(100);
    private final int maxTeamCount;

    MatchMode(int maxTeamCount) {
        this.maxTeamCount = maxTeamCount;
    }

    public int getMaxTeamCount() {
        return maxTeamCount;
    }
}
