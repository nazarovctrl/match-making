package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.exp.NotFoundException;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;

@NoRepositoryBean
public interface BasicRepository<T, D> extends JpaRepository<T, D> {
    default T loadById(D id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Not found"));
    }
}
