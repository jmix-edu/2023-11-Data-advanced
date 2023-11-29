package com.company.jmixpm.screen.employee;

import com.company.jmixpm.entity.EmployeeDetails;
import io.jmix.core.Metadata;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeEdit extends StandardEditor<Employee> {
    @Autowired
    private DataContext dataContext;
    @Autowired
    private Metadata metadata;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Employee> event) {
        EmployeeDetails details = dataContext.create(EmployeeDetails.class);
        //EmployeeDetails details = metadata.create(EmployeeDetails.class);  incorrect call
        event.getEntity().setEmployeeDetails(details);
        
    }
    
    
}