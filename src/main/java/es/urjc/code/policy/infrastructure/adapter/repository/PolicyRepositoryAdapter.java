package es.urjc.code.policy.infrastructure.adapter.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.policy.application.port.outgoing.LoadPolicyPort;
import es.urjc.code.policy.application.port.outgoing.UpdatePolicyPort;
import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.exception.EntityNotFoundException;
import es.urjc.code.policy.infrastructure.adapter.converter.PolicyEntityToPolicyConverter;
import es.urjc.code.policy.infrastructure.adapter.converter.PolicyToPolicyEntityConverter;
import es.urjc.code.policy.infrastructure.adapter.converter.PolicyVersionToPolicyVersionEntityConverter;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyVersionEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.jpa.PolicyJpaRepository;

@Service
@Transactional
public class PolicyRepositoryAdapter implements UpdatePolicyPort, LoadPolicyPort {
	
	private final PolicyJpaRepository policyJpaRepository;
	private final PolicyEntityToPolicyConverter policyEntityToPolicyConverter;
	private final PolicyVersionToPolicyVersionEntityConverter policyVersionToPolicyVersionEntityConverter;
	private final PolicyToPolicyEntityConverter policyToPolicyEntityConverter;
	
	@Autowired
	public PolicyRepositoryAdapter(PolicyJpaRepository policyJpaRepository,
			                       PolicyEntityToPolicyConverter policyEntityToPolicyConverter,
			                       PolicyVersionToPolicyVersionEntityConverter policyVersionToPolicyVersionEntityConverter,
			                       PolicyToPolicyEntityConverter policyToPolicyEntityConverter) {
		this.policyJpaRepository = policyJpaRepository;
		this.policyEntityToPolicyConverter = policyEntityToPolicyConverter;
		this.policyVersionToPolicyVersionEntityConverter = policyVersionToPolicyVersionEntityConverter;
		this.policyToPolicyEntityConverter = policyToPolicyEntityConverter;
	}

	@Override
	public Policy updateTerminateState(String policyNumber) {
		PolicyEntity policyEntity = policyJpaRepository.findByNumber(policyNumber).orElseThrow(()-> new EntityNotFoundException("Policy not found. Looking for policy with number: " + policyNumber));
		Policy policy = policyEntityToPolicyConverter.convert(policyEntity);
		policy.terminate(LocalDate.now());
		PolicyVersionEntity policyVersionEntity = policyVersionToPolicyVersionEntityConverter.convert(policy.versions().lastVersion());
		policyEntity.addPolicyVersion(policyVersionEntity);
		policyJpaRepository.save(policyEntity);
		return policy;
	}

	@Override
	public Policy createPolicy(Offer offer, Person policyHolder, AgentRef agent) {
		Policy policy = new Policy.Builder().withNumber(UUID.randomUUID().toString()).withAgent(agent).build();
        policy.addVersion(offer, policyHolder);
        final PolicyEntity policyEntity = policyToPolicyEntityConverter.convert(policy);
        policyJpaRepository.save(policyEntity);
		return policy;
	}

	@Override
	public Policy getPolicy(String policyNumber) {
		PolicyEntity policyEntity = policyJpaRepository.findByNumber(policyNumber).orElseThrow(()-> new EntityNotFoundException("Policy not found. Looking for policy with number: " + policyNumber));
		return policyEntityToPolicyConverter.convert(policyEntity);
	}
	
}
