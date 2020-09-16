package es.codeurjc.policy.command.bus;

import es.codeurjc.policy.command.api.Command;

public interface CommandHandler<R, C extends  Command<R>> {
    R handle(C command);
}
