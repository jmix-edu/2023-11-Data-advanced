package com.company.jmixpm.screen.userprojectsdialog;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("UserProjectsDialog")
@UiDescriptor("user-projects-dialog.xml")
public class UserProjectsDialog extends Screen {

    private User user;
    @Autowired
    private CollectionLoader<Project> projectDl;

    public User getUser() {
        return user;
    }

    public UserProjectsDialog withUser(User user) {
        this.user = user;
        projectDl.setParameter("user", user);
        projectDl.load();

        return this;
    }
}