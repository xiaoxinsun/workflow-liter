package org.bill.wflite;

/**
 * A context object carrying the action performed during a workflow step.
 * Using a record simplifies the class definition for this immutable data carrier.
 */
public record TaskContext(String action) {
}
