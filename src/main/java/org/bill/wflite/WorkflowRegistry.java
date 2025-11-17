package org.bill.wflite;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class WorkflowRegistry {

    private final Map<String, WorkflowDefinition<?, ?, ?, ?>> registry = new ConcurrentHashMap<>();

    @Inject
    public WorkflowRegistry(Instance<WorkflowDefinition<?, ?, ?, ?>> definitions) {
        definitions.forEach(this::register);
    }

    public void register(WorkflowDefinition<?, ?, ?, ?> definition) {
        registry.put(definition.getName(), definition);
        System.out.println("[Registry] Workflow registered: " + definition.getName());
    }

    public Optional<WorkflowDefinition<?, ?, ?, ?>> get(String name) {
        return Optional.ofNullable(registry.get(name));
    }
}
