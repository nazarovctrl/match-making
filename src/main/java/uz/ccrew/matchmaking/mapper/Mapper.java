package uz.ccrew.matchmaking.mapper;

public interface Mapper<D, E> {
    E mapDTO(D d);

    D mapEntity(E e);
}
