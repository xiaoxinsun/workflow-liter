package org.bill.wflite;

/**
 * Represents a request to advance a workflow.
 * Using a record simplifies the class definition for this immutable data carrier.
 */
public record WorkflowStepRequest(
        String workflowName,
        String currentState,
        String action) {
}
