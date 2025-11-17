package org.bill.wflite;

import org.squirrelframework.foundation.fsm.Condition;

public class ActionCondition implements Condition<TaskContext> {
    private final String expectedAction;

    private ActionCondition(String expectedAction) {
        this.expectedAction = expectedAction;
    }

    public static ActionCondition is(String action) {
        return new ActionCondition(action);
    }

    @Override
    public boolean isSatisfied(TaskContext context) {
        // Use the record's accessor method 'action()'
        return context != null && expectedAction.equals(context.action());
    }

    @Override
    public String name() {
        return "ActionIs_" + expectedAction;
    }
}
