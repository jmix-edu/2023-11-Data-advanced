package com.company.jmixpm.screen.project;

import com.company.jmixpm.app.ProjectsService;
import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.screen.user.UserBrowse;
import io.jmix.audit.snapshot.EntitySnapshotManager;
import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Field;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.validator.BeanPropertyValidator;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
    @Autowired
    private Field<ProjectLabels> projectLabelsField;
    @Autowired
    private ProjectsService projectsService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Validator validator;
    @Autowired
    private EntitySnapshotManager entitySnapshotManager;

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        entitySnapshotManager.createSnapshot(getEditedEntity(), getEditedEntityContainer().getFetchPlan());
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        projectLabelsField.setEditable(true);

        event.getEntity().setProjectLabels(new ProjectLabels(List.of("bug", "task")));
    }

    @Subscribe
    public void onInit(final InitEvent event) {

//        Collection<io.jmix.ui.component.validation.Validator<ProjectLabels>> validators = new ArrayList<>(projectLabelsField.getValidators()); //jmix validator
//        for (io.jmix.ui.component.validation.Validator<ProjectLabels> fieldValidator : validators) {
//            if (fieldValidator instanceof BeanPropertyValidator) {  //created when component is bound to entity attribute
//                ((BeanPropertyValidator) fieldValidator).setValidationGroups(new Class[]{UiCrossFieldChecks.class});  //validation group is added
//                projectLabelsField.removeValidator(fieldValidator);  //remove validator
//            }
//        }
//          setCrossFieldValidate(false);
    }

    @Subscribe("commitWithBeanValidation")
    public void onCommitWithBeanValidationClick(final Button.ClickEvent event) {

        try {

            projectsService.saveProject(getEditedEntity());
            close(new StandardCloseAction(Window.COMMIT_ACTION_ID, false));
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                sb.append(constraintViolation.getMessage()).append("\n");
            }
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(sb.toString())
                    .show();


        }
    }

    @Subscribe("performBeanValidationBtn")
    public void onPerformBeanValidationBtnClick(final Button.ClickEvent event) {
        Set<ConstraintViolation<Project>> violations = validator.validate(getEditedEntity(),
                Default.class, UiComponentChecks.class, UiCrossFieldChecks.class);

        showBeanValidationExceptions(violations);

    }

    private void showBeanValidationExceptions(Set<ConstraintViolation<Project>> violations) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : violations) {
            sb.append(constraintViolation.getMessage()).append("\n");
        }
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(sb.toString())
                .show();
    }

    @Install(to = "usersTable.add", subject = "screenConfigurer")
    private void usersTableAddScreenConfigurer(Screen screen) {
        ((UserBrowse) screen).setFilterProject(getEditedEntity());
    }

}