package org.bill.wflite;

import java.util.Map;

public record WfEvent(
        String action,
        Map<String, Object> actionData) {
}
