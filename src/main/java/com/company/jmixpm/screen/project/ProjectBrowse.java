package com.company.jmixpm.screen.project;

import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {

    @Autowired
    private CollectionContainer<Project> projectsDc;

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe("dmCreate")
    public void onDmCreateClick(Button.ClickEvent event) {
        Project project = dataManager.create(Project.class);
        project.setName("Project " + RandomStringUtils.randomAlphabetic(5));
        User user = (User) currentAuthentication.getUser();
        project.setManager(user);

        Project saved = dataManager.unconstrained().save(project);

        projectsDc.getMutableItems().add(saved);
    }
    @Subscribe
    public void onInit(InitEvent event) {
        Task task = dataManager.load(Task.class)
                .all()
                .one();

        Project project = task.getProject();

        project.getName();
    }
}