package org.bill.wflite;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.squirrelframework.foundation.component.SquirrelProvider;
import org.squirrelframework.foundation.fsm.DotVisitor;
import org.squirrelframework.foundation.fsm.StateMachine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class MyWorkflowEngine {

    @Inject
    WorkflowRegistry registry;

    /**
     * Starts a new instance of the specified workflow.
     *
     * @param workflowName The name of the workflow to start.
     * @return The newly created state machine instance, already transitioned to its first state.
     */
    public StateMachine startWorkflow(String workflowName) {
        WorkflowDefinition def = registry.get(workflowName)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found: " + workflowName));

        StateMachine fsm = def.getBuilder().newStateMachine(def.getInitialState());
        fsm.fire(def.getStartEvent());
        return fsm;
    }

    // up start, any action

    /**
     * Calculates the next state of a workflow given a current state and an action.
     *
     * @param workflowName     The name of the workflow.
     * @param currentStateName The name of the current state.
     * @param action           The action performed by the user or system.
     * @return A response containing the new state and whether the workflow has terminated.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public WorkflowStepResponse getNextState(String workflowName, String currentStateName, String action) {
        WorkflowDefinition def = registry.get(workflowName)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found: " + workflowName));

        // Convert the string state name from the request back to the actual enum type.
        Object currentState = Enum.valueOf(def.getStateClass(), currentStateName);

        // Create a new FSM instance at the specified current state.
        StateMachine fsm = def.getBuilder().newStateMachine(currentState);
        fsm.fire(def.getStepEvent(), def.createContext(action));

        Object newState = fsm.getCurrentState();
        boolean isTerminated = def.getTerminalStates().contains(newState);

        // The response DTO should contain the state name as a string for serialization.
        return new WorkflowStepResponse(newState.toString(), isTerminated);
    }

    /**
     * Generates a DOT graph definition for a given workflow.
     *
     * @param workflowName The name of the workflow to visualize.
     * @return A string containing the DOT graph definition.
     */
    public String getWorkflowDotDefinition(String workflowName) throws IOException {
        WorkflowDefinition def = registry.get(workflowName)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found: " + workflowName));

        DotVisitor visitor = SquirrelProvider.getInstance().newInstance(DotVisitor.class);
        StateMachine fsm = def.getBuilder().newStateMachine(def.getInitialState());
        fsm.accept(visitor);

        var file = "workflow.dot";
        visitor.convertDotFile(file);
        return Files.readString(Paths.get(file));
    }
}
