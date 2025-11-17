package org.bill.wflite;

import org.bill.wflite.tasks.FileInfo;

import java.util.List;
import java.util.Map;

public record WfContext(
        Map<String, Object> businessData,
        List<FileInfo> attachments
) {
}
