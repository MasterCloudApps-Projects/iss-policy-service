package es.urjc.code.policy.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import es.urjc.code.policy.infrastructure.adapter.kafka.PoliciesStreams;

@EnableBinding(PoliciesStreams.class)
public class StreamsConfig {

}
