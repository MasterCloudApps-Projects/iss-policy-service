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
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.exception.EntityNotFoundException;
import es.urjc.code.policy.infrastructure.adapter.repository.jpa.PolicyJpaRepository;

@Service
@Transactional
public class PolicyRepositoryAdapter implements UpdatePolicyPort, LoadPolicyPort {
	
	private final PolicyJpaRepository policyJpaRepository;
	
	@Autowired
	public PolicyRepositoryAdapter(PolicyJpaRepository policyJpaRepository) {
		this.policyJpaRepository = policyJpaRepository;
	}

	@Override
	public Policy updateTerminateState(String policyNumber) {
		Policy policy = policyJpaRepository.findByNumber(policyNumber).orElseThrow(()-> new EntityNotFoundException("Policy not found. Looking for policy with number: " + policyNumber));
		policy.terminate(LocalDate.now());
		PolicyVersion policyVersion = policy.versions().lastVersion();
		policy.addPolicyVersion(policyVersion);
		policyJpaRepository.save(policy);
		return policy;
	}

	@Override
	public Policy createPolicy(Offer offer, Person policyHolder, AgentRef agent) {
		Policy policy = new Policy.Builder().withNumber(UUID.randomUUID().toString()).withAgent(agent).build();
        policy.addVersion(offer, policyHolder);
        policyJpaRepository.save(policy);
		return policy;
	}

	@Override
	public Policy getPolicy(String policyNumber) {
		return policyJpaRepository.findByNumber(policyNumber).orElseThrow(()-> new EntityNotFoundException("Policy not found. Looking for policy with number: " + policyNumber));
	}
	
}
