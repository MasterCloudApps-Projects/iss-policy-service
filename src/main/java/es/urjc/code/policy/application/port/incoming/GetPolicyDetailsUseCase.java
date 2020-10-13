package es.urjc.code.policy.application.port.incoming;

import es.codeurjc.policy.command.bus.QueryHandler;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQuery;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQueryResult;

public interface GetPolicyDetailsUseCase extends QueryHandler<GetPolicyDetailsQueryResult, GetPolicyDetailsQuery> {

	public GetPolicyDetailsQueryResult handle(GetPolicyDetailsQuery query);
}
