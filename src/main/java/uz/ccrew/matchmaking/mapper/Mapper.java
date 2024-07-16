package uz.ccrew.matchmaking.mapper;

public interface Mapper<D, E> {
    E toEntity(D d);

    D toDTO(E e);
}
