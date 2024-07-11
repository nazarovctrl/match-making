package uz.ccrew.matchmaking.dto;

import uz.ccrew.matchmaking.enums.UserRole;

public record UserDTO (Integer id,String login, UserRole userRole){

}
