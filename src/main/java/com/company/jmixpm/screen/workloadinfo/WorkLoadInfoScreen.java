package com.company.jmixpm.screen.workloadinfo;

import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.model.KeyValueContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("WorkLoadInfoScreen")
@UiDescriptor("work-load-info-screen.xml")
public class WorkLoadInfoScreen extends Screen {
    @Autowired
    private KeyValueContainer workloadDc;
    @Autowired
    private Notifications notifications;

    public WorkLoadInfoScreen withItem (KeyValueEntity item) {
        workloadDc.setItem(item);

        return this;
    }

    @Subscribe("closeWindow")
    public void onCloseWindow(final Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }

    @Subscribe(id = "workloadDc", target = Target.DATA_CONTAINER)
    public void onWorkloadDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<KeyValueEntity> event) {
        notifications.create()
                .withCaption("Property changed: " + event.getProperty())
                .show();
    }
    
    
    
    
}