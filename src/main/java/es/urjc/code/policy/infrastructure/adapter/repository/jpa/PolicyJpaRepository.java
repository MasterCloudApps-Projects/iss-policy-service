package es.urjc.code.policy.infrastructure.adapter.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.urjc.code.policy.domain.Policy;

@Repository
public interface PolicyJpaRepository extends JpaRepository<Policy, UUID>{

	@Query("SELECT p FROM Policy p LEFT JOIN FETCH p.versions v LEFT JOIN FETCH v.covers where p.number=:number")
	Optional<Policy> findByNumber(@Param("number") String number);
}
