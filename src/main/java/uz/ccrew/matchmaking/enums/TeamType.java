package uz.ccrew.matchmaking.enums;

public enum TeamType {
    SOLO(1), SQUAD(4);

    private final int playerCount;

    TeamType(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}