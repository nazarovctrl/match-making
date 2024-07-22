package uz.ccrew.matchmaking.mapper;

import java.util.List;

public interface Mapper<C, D, E> {
    E toEntity(C c);

    D toDTO(E e);

    default List<D> toDTOList(List<E> eList) {
        return eList.stream().map(this::toDTO).toList();
    }
}
