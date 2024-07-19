package uz.ccrew.matchmaking.enums;

public enum TeamType {
    SOLO(1),
    SQUAD(4);

    private final int maxPlayersCount;

    TeamType(int maxPlayersCount) {
        this.maxPlayersCount = maxPlayersCount;
    }

    public int getMaxPlayersCount() {
        return maxPlayersCount;
    }
}