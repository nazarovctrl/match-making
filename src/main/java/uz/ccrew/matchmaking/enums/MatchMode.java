package uz.ccrew.matchmaking.enums;

public enum MatchMode {
    TDM(2), FFA(5);
    private final int teamCount;

    MatchMode(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getTeamCount() {
        return teamCount;
    }
}
