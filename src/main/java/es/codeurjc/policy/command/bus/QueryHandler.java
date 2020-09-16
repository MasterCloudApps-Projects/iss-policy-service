package es.codeurjc.policy.command.bus;

import es.codeurjc.policy.command.api.Query;

public interface QueryHandler<R, C extends Query<R>> {
    R handle(C query);
}
