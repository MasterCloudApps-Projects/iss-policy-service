package es.codeurjc.policy.command.bus;

import es.codeurjc.policy.command.api.Command;
import es.codeurjc.policy.command.api.Query;

public interface Bus {
    <R,C extends Command<R>> R executeCommand(C command);
    <R,Q extends Query<R>> R executeQuery(Q query);
}
