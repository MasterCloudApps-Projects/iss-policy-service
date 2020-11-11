package es.urjc.code.policy.queries;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.policy.application.port.incoming.GetPolicyDetailsUseCase;
import es.urjc.code.policy.application.port.outgoing.LoadPolicyPort;
import es.urjc.code.policy.domain.Cover;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQuery;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQueryResult;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.dto.PolicyDetailsDto;

@Component
public class GetPolicyDetailsQueryHandler implements GetPolicyDetailsUseCase {

	private final LoadPolicyPort loadPolicyPort;

	@Autowired
	public GetPolicyDetailsQueryHandler(LoadPolicyPort loadPolicyPort) {
		this.loadPolicyPort = loadPolicyPort;
	}

	@Transactional
	@Override
	public GetPolicyDetailsQueryResult handle(GetPolicyDetailsQuery query) {
		final Policy policy = loadPolicyPort.getPolicy(query.getNumber());
		return new GetPolicyDetailsQueryResult.Builder().withPolicy(createPolicyDetailsDto(policy)).build();
	}

	private PolicyDetailsDto createPolicyDetailsDto(Policy policy) {

		PolicyVersion policyVersion = policy.versions().lastVersion();
        if (policyVersion!=null) {
    		return new PolicyDetailsDto.Builder().withNumber(policy.getNumber())
    				.withDateFrom(policyVersion.getVersionValidityPeriod().getFrom())
    				.withDateTo(policyVersion.getVersionValidityPeriod().getTo())
    				.withPolicyHolder(policyVersion.getPolicyHolder().getFullName())
    				.withTotalPremium(policyVersion.getTotalPremiumAmount()).withProductCode(policyVersion.getProductCode())
    				.withAccountNumber(policyVersion.getAccountNumber())
    				.withCovers(
    						policyVersion.getCovers().stream().map(Cover::getCode).sorted().collect(Collectors.toSet()))
    				.build();
        	
        }
        return null;
	}
}
