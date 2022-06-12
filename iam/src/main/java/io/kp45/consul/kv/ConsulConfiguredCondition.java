package io.kp45.consul.kv;


import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConsulConfiguredCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Boolean consulEnable = context.getEnvironment().getProperty("spring.cloud.consul.enable", Boolean.class, true);
        return consulEnable ? ConditionOutcome.match("Consul is enabled") : ConditionOutcome.noMatch("Consul is disabled");
    }

}
