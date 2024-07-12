package uz.ccrew.matchmaking.enums;

import java.util.Arrays;

public enum UserRole {
    ADMINISTRATOR, SERVER, PLAYER;

    public static String[] all() {
        return Arrays.stream(UserRole.values()).map(Enum::name).toArray(String[]::new);
    }
}
