package com.store.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class PropertiesCopier {

    public void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getPropertyNames(src));
    }

    private String[] getPropertyNames(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (isList(propertyDescriptor) || isEntity(propertyDescriptor)) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private boolean isList(PropertyDescriptor propertyDescriptor) {
        return Collection.class.isAssignableFrom(propertyDescriptor.getPropertyType());
    }

    private boolean isEntity(PropertyDescriptor propertyDescriptor) {
        return Arrays.stream(propertyDescriptor.getPropertyType().getDeclaredAnnotations()).anyMatch(a -> a.annotationType().equals(javax.persistence.Entity.class));
    }

}
