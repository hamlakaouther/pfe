package dz.elit.pfe.commun.service;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author leghettas.rabah
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
//    public void create(T entity) {
//        try {
//            getEntityManager().persist(entity);
//        } catch (ConstraintViolationException e) {
//            System.out.println("e ======================================== " + e.getMessage());
//            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//            for (ConstraintViolation<?> cv : constraintViolations) {
//                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
//            }
//        }
//    }
//
//    public void edit(T entity) {
//        try {
//            getEntityManager().merge(entity);
//        } catch (ConstraintViolationException e) {
//            System.out.println("e ====================================== " + e.getMessage());
//            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//            for (ConstraintViolation<?> cv : constraintViolations) {
//                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
//            }
//        }
//    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findAllDescending(String column, boolean visibility) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root c = cq.from(entityClass);
        cq.select(c);
        cq.orderBy(cb.desc(c.get(column)));
        Query query = getEntityManager().createQuery(cq);
         return query.getResultList();
    }

    public List<T> findAllOrderById() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root c = cq.from(entityClass);
        cq.select(c);
        cq.orderBy(cb.desc(c.get("id")));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findAllOrderByAttribut(String attribut, boolean visibility) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root c = cq.from(entityClass);
        cq.select(c);
        cq.orderBy(cb.desc(c.get(attribut)));
        Query query = getEntityManager().createQuery(cq);
        return query.getResultList();
    }

    public List<T> findAllOrderByAttributAsc(String attribut, boolean visibility) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root c = cq.from(entityClass);
        cq.select(c);
        cq.orderBy(cb.asc(c.get(attribut)));
        Query query = getEntityManager().createQuery(cq);
        return query.getResultList();
    }

    public List<T> findRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        jakarta.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        jakarta.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        jakarta.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findAllLazy(int first, int pageSize) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> myObj = cq.from(entityClass);

        cq.select(cq.from(entityClass));
        cq.orderBy(cb.asc(myObj.get("id")));
        Query query = getEntityManager().createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<T> findByAttribute(String attribute, Object value) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<T> c = q.from(entityClass);
        q.where(cb.equal(c.get(attribute), value));
        List<T> listResult = getEntityManager().createQuery(q).getResultList();
        return listResult;
    }
    
    public List<T> findByAttributeSorted(String attribute, Object value, String sortField, String sortOrder) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<T> c = q.from(entityClass);
        q.where(cb.equal(c.get(attribute), value));
       if (sortField != null) {
            if (sortOrder.equals("ASCENDING")) {
                q.orderBy(cb.asc(c.get(sortField)));
            } else {
                q.orderBy(cb.desc(c.get(sortField)));
            }
        }
        List<T> listResult = getEntityManager().createQuery(q).getResultList();
        return listResult;
    }

    public T findByAttributeSingle(String attribute, Object value) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery q = cb.createQuery();
            Root<T> c = q.from(entityClass);
            q.where(cb.equal(c.get(attribute), value));
            T result;
            result = (T) getEntityManager().createQuery(q).getSingleResult();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
