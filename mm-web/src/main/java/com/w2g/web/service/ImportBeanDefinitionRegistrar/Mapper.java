package com.w2g.web.service.ImportBeanDefinitionRegistrar;

import java.lang.annotation.*;

/**
 * Created by W2G on 2019/11/5.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface Mapper {
}


