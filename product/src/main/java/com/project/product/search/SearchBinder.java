package com.project.product.search;

import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * 
 */
public class SearchBinder {
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getObjectIdValue(Search entity) {

        Class<? extends Object> clazz = entity.getClass();

        for (Method method: clazz.getMethods()){
            SearchCondition annotation = method.getAnnotation(SearchCondition.class);

            if (annotation != null) {
                try {
                    Object value = method.invoke(entity);
                    if (!StringUtils.isEmpty(value)) {
                        Object ref = null;

                        if (annotation.qualifiedBy() != void.class) {
                            ref = makeId(annotation, value);
                        }

                        switch (annotation.condition()) {
                            case EQUAL:
                                entity.getSpecs().add(SpecificationUtil.equalTo(annotation.value(), ref == null ? value : ref));
                                break;
                            case NOTEQUAL:
                                entity.getSpecs().add(SpecificationUtil.notEqualTo(annotation.value(), ref == null ? value : ref));
                                break;
                            case LIKE:
                                entity.getSpecs().add(SpecificationUtil.like(annotation.value(), (String) value));
                                break;
                            case IN:
                                entity.getSpecs().add(SpecificationUtil.in(annotation.value(), (Collection) value));
                                break;  
                            case NOT_IN:
                                entity.getSpecs().add(SpecificationUtil.notin(annotation.value(), (Collection) value));
                                break;
                            case JOIN:
                                entity.getSpecs().add(SpecificationUtil.equalToJoin(annotation.table(), annotation.value(), ref == null ? value : ref));
                                break;
                            case LEFTJOIN:
                                entity.getSpecs().add(SpecificationUtil.equalToLeftJoin(annotation.table(), annotation.value(), ref == null ? value : ref));
                                break;
                            case GREATER:
                                if(annotation.value()!=null)
                                    entity.getSpecs().add(SpecificationUtil.greater(annotation.value(), value));
                                break;
                            case LESS:
                                if(annotation.value()!=null)
                                    entity.getSpecs().add(SpecificationUtil.less(annotation.value(),  value));
                                break;
                            case GREATER_AND_EQUAL:
                                if(annotation.value()!=null)
                                    entity.getSpecs().add(SpecificationUtil.greaterAndEqual(annotation.value(), value));
                                break;
                            case LESS_AND_EQUAL:
                                if(annotation.value()!=null)
                                    entity.getSpecs().add(SpecificationUtil.lessAndEqual(annotation.value(),  value));
                                break;
                            case NOTNULL:
                                if(annotation.value()!= null && value.equals(Boolean.TRUE))
                                    entity.getSpecs().add(SpecificationUtil.notNull(annotation.value()));
                                break;
                            case NULL:
                                if(annotation.value()!= null && value.equals(Boolean.TRUE))
                                    entity.getSpecs().add(SpecificationUtil.isNull(annotation.value()));
                                break;
                            case LIKE_LIST:
                                for (Object v : (List) value) {
                                    entity.getSpecs().add(SpecificationUtil.like(annotation.value(), (String) v));
                                }
                                break;
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Field field : clazz.getDeclaredFields()) {

            SearchCondition annotation = field.getAnnotation(SearchCondition.class);

            if (annotation != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (!StringUtils.isEmpty(value)) {
                        Object ref = null;

                        if (annotation.qualifiedBy() != void.class) {
                            ref = makeId(annotation, value);
                        }

                        switch (annotation.condition()) {
                            case EQUAL:
                                entity.getSpecs().add(SpecificationUtil.equalTo(annotation.value(), ref == null ? value : ref));
                                break;
                            case NOTEQUAL:
                                entity.getSpecs().add(SpecificationUtil.notEqualTo(annotation.value(), ref == null ? value : ref));
                                break;
                            case LIKE:
                                entity.getSpecs().add(SpecificationUtil.like(annotation.value(), (String) value));
                                break;
                            case IN:
                                entity.getSpecs().add(SpecificationUtil.in(annotation.value(), (Collection) value));
                                break;
                            case NOT_IN:
                                entity.getSpecs().add(SpecificationUtil.notin(annotation.value(), (Collection) value));
                                break;
                            case JOIN:
                                entity.getSpecs().add(SpecificationUtil.equalToJoin(annotation.table(), annotation.value(), ref == null ? value : ref));
                                break;
                            case LEFTJOIN:
                                entity.getSpecs().add(SpecificationUtil.equalToLeftJoin(annotation.table(), annotation.value(), ref == null ? value : ref));
                                break;
                            case GREATER:
                                entity.getSpecs().add(SpecificationUtil.greater(annotation.value(), value));
                                break;
                            case LESS:
                                entity.getSpecs().add(SpecificationUtil.less(annotation.value(),  value));
                                break;
                            case GREATER_AND_EQUAL:
                                entity.getSpecs().add(SpecificationUtil.greaterAndEqual(annotation.value(), value));
                                break;
                            case LESS_AND_EQUAL:
                                entity.getSpecs().add(SpecificationUtil.lessAndEqual(annotation.value(),  value));
                                break;
                            case NOTNULL:
                                if(annotation.value()!= null && value.equals(Boolean.TRUE))
                                    entity.getSpecs().add(SpecificationUtil.notNull(annotation.value()));
                                break;
                            case NULL:
                                if(annotation.value()!= null && value.equals(Boolean.TRUE))
                                    entity.getSpecs().add(SpecificationUtil.isNull(annotation.value()));
                                break;
                            case LIKE_LIST:
                                for (Object v : (List) value) {
                                    entity.getSpecs().add(SpecificationUtil.like(annotation.value(), (String) v));
                                }
                                break;
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object makeId(SearchCondition annotation, Object value) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Object ref = annotation.qualifiedBy().newInstance();

        List<Field> fields = new ArrayList<Field>();

        Class<?> clazz = ref.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }

        for (Field field : fields) {
            Id idAnnotation = field.getAnnotation(Id.class);

            if (idAnnotation != null) {
                field.setAccessible(true);
                field.set(ref, value);
            }
        }

        return ref;
    }
}
