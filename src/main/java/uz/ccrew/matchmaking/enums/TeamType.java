package uz.ccrew.matchmaking.enums;

public enum TeamType {
    SOLO(1), SQUAD(4);
    // DUO, TRIO
    private final int playerCount;

    TeamType(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
