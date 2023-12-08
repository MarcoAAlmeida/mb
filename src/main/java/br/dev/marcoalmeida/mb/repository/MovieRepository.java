package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Movie;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepository extends CrudRepository<Movie, String>, JpaSpecificationExecutor<Movie> {
    Optional<Movie> findByTitle(@Param("title")String title);

    default Page<Movie> findByIdNotIn(Set<String> ids, PageRequest pageRequest) {
        return findAll(idIsNotInSet(ids), pageRequest);
    }
    default long countByIdNotIn(Set<String> ids) {
        return count(idIsNotInSet(ids));
    }
    default Specification<Movie> idIsNotInSet(Set<String> usedIds){
        return new Specification<Movie>() {
            @Override
            public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return usedIds.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.not(root.get("id").in(usedIds));
            }
        };
    }



}