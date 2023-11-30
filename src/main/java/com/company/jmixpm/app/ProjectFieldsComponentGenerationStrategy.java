package com.company.jmixpm.app;

import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.datatype.ProjectLabelsDatatype;
import com.company.jmixpm.entity.Project;
import io.jmix.core.JmixOrder;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.core.metamodel.model.Range;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.ComponentGenerationContext;
import io.jmix.ui.component.ComponentGenerationStrategy;
import io.jmix.ui.component.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class ProjectFieldsComponentGenerationStrategy implements ComponentGenerationStrategy, Ordered {
    @Autowired
    private UiComponents uiComponents;

    @Nullable
    @Override
    public io.jmix.ui.component.Component createComponent(ComponentGenerationContext context) {
        if (!"projectLabels".equals(context.getProperty())) {
            return null;
        }

        MetaClass metaClass = context.getMetaClass();
        if (!metaClass.getJavaClass().equals(Project.class)) {
            return null;
        }
        MetaProperty property = metaClass.getProperty(context.getProperty());
        Range range = property.getRange();

        if (range.isDatatype()) {
            Datatype projectLabelsDatatype = range.asDatatype();
            if (projectLabelsDatatype instanceof ProjectLabelsDatatype) {
                return create(context);
            }
        }

        return null;
    }

    private TextArea<ProjectLabels> create(ComponentGenerationContext context) {
        TextArea<ProjectLabels> component = uiComponents.create(TextArea.NAME);
        component.setRows(2);
        component.setValueSource(context.getValueSource());
        return component;
    }

    @Override
    public int getOrder() {
        return JmixOrder.HIGHEST_PRECEDENCE + 10;
    }
}