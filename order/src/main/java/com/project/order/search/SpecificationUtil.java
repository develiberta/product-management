package com.project.order.search;

import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;

/*
 * 
 */
@SuppressWarnings("rawtypes")
public class SpecificationUtil {	
	public static Specification notNull(String column) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(column));
    }

    public static Specification isNull(String column) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNull(root.get(column));
    }


    public static Path getPath(Root root, String column){
        Path p = root;
        if(column.contains(".")){
            String[] columns = column.split("\\.");

            for(int i = 0; i< columns.length;i++){
                Path c = p.get(columns[i]);

                if(c instanceof PluralAttributePath){
                    p = root.joinList(columns[i]);
                }else{
                    p = p.get(columns[i]);
                }
            }
            return p;
        }else{
            return root.get(column);
        }

    }

    public static Specification equalTo(String column, Object value) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(getPath(root, column), value);
    }

    public static Specification equalToJoin(String table, String column, Object value) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            Join join = root.join(table, JoinType.INNER);
            return criteriaBuilder.equal(join.get(column), value);
        };
    }

    public static Specification equalToLeftJoin(String table, String column, Object value) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            Join join = root.join(table, JoinType.LEFT);
            return criteriaBuilder.equal(join.get(column), value);
        };
    }

    public static Specification notEqualTo(String column, Object value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(getPath(root, column), value);
    }

	@SuppressWarnings("unchecked")
	public static Specification like(String column, String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(getPath(root, column)), "%" + value.toLowerCase() + "%");
    }

    @SuppressWarnings("unchecked")
	public static Specification in(String column, Collection value) {
        return (root, criteriaQuery, criteriaBuilder) -> {
			CriteriaBuilder.In in = criteriaBuilder.in(root.get(column));
            value.forEach(p -> in.value(p));
            return in;
        };
    }
    
    /* */
    @SuppressWarnings("unchecked")
	public static Specification notin(String column, Collection value) {
        return (root, criteriaQuery, criteriaBuilder) -> {
			CriteriaBuilder.In in = criteriaBuilder.in(root.get(column));
            value.forEach(p -> in.value(p));
            return in.not();
        };
    }
    

    @SuppressWarnings("unchecked")
	public static Specification greater(String column, Object value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(getPath(root, column), (Comparable) value) ;
    }

    @SuppressWarnings("unchecked")
	public static Specification greaterAndEqual(String column, Object value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(getPath(root, column), (Comparable) value);
    }

    @SuppressWarnings("unchecked")
	public static Specification less(String column, Object value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(getPath(root, column), (Comparable) value);
    }

    @SuppressWarnings("unchecked")
	public static Specification lessAndEqual(String column, Object value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(getPath(root, column), (Comparable) value);
    }

}
