package uz.ccrew.matchmaking.enums;

public enum Rank {
    BRONZE(0, 100), SILVER(101, 200), GOLD(201, 300),
    PLATINUM(301, 450), DIAMOND(451, 600), MASTER(601, 750),
    LEGEND(751, 1000);

    private final Integer min;
    private final Integer max;

    Rank(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }
}
