package org.bill.wflite;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.squirrelframework.foundation.fsm.StateMachine;

import java.io.IOException;
import java.util.Random;

@Path("/workflow")
@Produces(MediaType.APPLICATION_JSON)
public class WorkflowResource {

    @Inject
    MyWorkflowEngine engine;

    /**
     * Starts a workflow and performs the first automatic step.
     */
    @POST
    @Path("/{workflowName}/start")
    public WorkflowStepResponse startWorkflow(@PathParam("workflowName") String workflowName) {
        // 1. Start the FSM and fire the initial event.
        StateMachine fsm = engine.startWorkflow(workflowName);
        String currentState = fsm.getCurrentState().toString();
        System.out.println("[API] Workflow '" + workflowName + "' started. Initial state: " + currentState);

        // 2. Simulate immediate, automatic business logic for the first state.
        //    In a real app, this might be a service call.
        System.out.println("[API-Logic] Processing '" + currentState + "' logic...");
        boolean isSuccess = new Random().nextBoolean();
        String action = isSuccess ? "success" : "failure";
        System.out.println("[API-Logic] '" + currentState + "' result: " + action);

        // 3. Ask the engine for the next state based on the logic's outcome.
        WorkflowStepResponse response = engine.getNextState(workflowName, currentState, action);
        System.out.println("[API] '" + currentState + "' auto-completed. New state: " + response.newState());
        return response;
    }

    /**
     * Advances a workflow from a given state based on a user-provided action.
     */
    @POST
    @Path("/step")
    public WorkflowStepResponse stepWorkflow(WorkflowStepRequest request) {
        System.out.println("[API] Received step request. Workflow: '" + request.workflowName() +
                "', Current: " + request.currentState() + ", Action: " + request.action());

        WorkflowStepResponse response = engine.getNextState(
                request.workflowName(),
                request.currentState(),
                request.action()
        );

        System.out.println("[API] Step complete. New state: " + response.newState());
        return response;
    }

    /**
     * Generates the workflow visualization in DOT graph format.
     */
    @GET
    @Path("/{workflowName}/visualize")
    @Produces(MediaType.TEXT_PLAIN)
    public String visualizeWorkflow(@PathParam("workflowName") String workflowName) throws IOException {
        return engine.getWorkflowDotDefinition(workflowName);
    }
}
