package com.company.jmixpm.app;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.ProjectStats;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanRepository;
import io.jmix.core.FetchPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProjectStatsService {

    private DataManager dataManager;
    @Autowired
    private FetchPlans fetchPlans;
    @Autowired
    private FetchPlanRepository fetchPlanRepository;

    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStats> fetchProjectStatistics() {
        List<Project> projects = dataManager.load(Project.class)
                .all()
                .fetchPlan("project-with-tasks")
                .list();
        List<ProjectStats> projectStats = projects.stream()
                .map(project -> {
                    ProjectStats stats = dataManager.create(ProjectStats.class);
                    stats.setProjectName(project.getName());

                    List<Task> tasks = project.getTasks();
                    stats.setTasksCount(tasks.size());

                    stats.setPlannedEfforts(project.getTotalEstimatedEfforts() == null
                            ? 0 : project.getTotalEstimatedEfforts());

                    stats.setActualEfforts(getActualEfforts(project.getId()));
                    return stats;
                })
                .toList();
        return projectStats;

    }

    private Integer getActualEfforts(UUID id) {

        return dataManager.loadValue("select sum(te.timeSpent) from TimeEntry te " +
                "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId", id)
                .one();
    }

//    private FetchPlan createFetchPlanWithTasks() {
//        return fetchPlans.builder(Project.class)
//                .addFetchPlan(FetchPlan.INSTANCE_NAME)
//                .add("totalEstimatedEfforts")
//                .add("tasks", fetchPlanBuilder ->
//                        fetchPlanBuilder.add("estimatedEfforts").add("startDate"))
//                .build();
//    }
}