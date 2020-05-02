package com.github.niupengyu.core.annotation;

import com.github.niupengyu.core.util.StringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class AutoConfigCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Map<String, Object> autoConfig=annotatedTypeMetadata.getAnnotationAttributes(AutoConfig.class.getName());
        Object propertiesName = autoConfig.get("name");
        boolean flag = StringUtil.booleanValueOf(autoConfig.get("def"),false);
        if (propertiesName != null) {
            String value = conditionContext.getEnvironment().getProperty(propertiesName.toString());
            if (value != null) {
                boolean val=Boolean.parseBoolean(value);
                flag=val;
            }
        }
        return new ConditionOutcome(flag, "none get properties");
    }
}
