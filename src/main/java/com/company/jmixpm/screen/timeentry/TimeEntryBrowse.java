package com.company.jmixpm.screen.timeentry;

import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.TimeEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("TimeEntry.browse")
@UiDescriptor("time-entry-browse.xml")
@LookupComponent("timeEntriesTable")
public class TimeEntryBrowse extends StandardLookup<TimeEntry> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;

    @Install(to = "timeEntriesDl", target = Target.DATA_LOADER)
    private List<TimeEntry> timeEntriesDlLoadDelegate(LoadContext<TimeEntry> loadContext) {
//        return dataManager.loadList(loadContext);
        return unconstrainedDataManager.loadList(loadContext);
    }
}