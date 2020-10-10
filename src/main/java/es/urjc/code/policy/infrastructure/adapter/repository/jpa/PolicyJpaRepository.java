package es.urjc.code.policy.infrastructure.adapter.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyEntity;

@Repository
public interface PolicyJpaRepository extends JpaRepository<PolicyEntity, UUID>{

	@Query("SELECT p FROM PolicyEntity p LEFT JOIN FETCH p.versions where p.number=:number")
	Optional<PolicyEntity> findByNumber(@Param("number") String number);
}
