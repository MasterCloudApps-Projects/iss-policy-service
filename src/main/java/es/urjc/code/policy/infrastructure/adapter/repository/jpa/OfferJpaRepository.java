package es.urjc.code.policy.infrastructure.adapter.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferEntity;

@Repository
public interface OfferJpaRepository extends JpaRepository<OfferEntity, UUID> {
	
	OfferEntity getByNumber(String number);
}
