package org.bill.wflite;

import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.StateMachineContext;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import java.util.Set;

/**
 * Defines a contract for a workflow, including its structure, states, and events.
 *
 * @param <T> The type of the state machine, extending UntypedStateMachine.
 * @param <S> The type of the states (usually an enum or String).
 * @param <E> The type of the events (usually an enum or String).
 */
public interface WorkflowDefinition<T extends StateMachine<T, WfNode, WfEvent, WfContext>> {
    /**
     * The unique name of the workflow.
     */
    String getName();

    /**
     * The state machine builder that contains the workflow's structure.
     */
    StateMachineBuilder<T, WfNode, WfEvent, WfContext> getBuilder();

    /**
     * A set of states that are considered terminal or final.
     */
    Set<WfNode> getTerminalStates();

    /**
     * The initial state of the workflow.
     */
    WfNode getInitialState();

    /**
     * The event that triggers the first transition from the initial state.
     */
    WfEvent getStartEvent();

    /**
     * The event used to advance the workflow from one step to the next.
     */
    WfEvent getStepEvent();

    /**
     * A factory method to create a context object from a given action string.
     */
    WfContext createContext(String action, StateMachineContext stateMachineContext);

}
