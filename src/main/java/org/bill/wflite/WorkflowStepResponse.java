package org.bill.wflite;

/**
 * Represents the result of a workflow step.
 * Using a record simplifies the class definition for this immutable data carrier.
 */
public record WorkflowStepResponse(
        String newState,
        boolean isTerminated) {
}
