package org.yeffrey.cheesecake.features.courseactivity.list;

import java.util.UUID;

public record CourseActivity(UUID activityId, String activityName, String sectionTypeId, String sectionDescription) {
}
