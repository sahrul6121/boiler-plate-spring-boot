package base.project.restapi.helper;

import base.project.restapi.classes.PaginationResult;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@AllArgsConstructor
public class PaginationHelper<BaseModel> {
    EntityManager entityManager;

    Pageable pageable;

    public PaginationResult<BaseModel> apply(
        CriteriaQuery<BaseModel> criteriaQuery,
        Class<BaseModel> baseModelClass
    ) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaTotal = criteriaBuilder.createQuery(Long.class);

        criteriaTotal.select(criteriaBuilder.count(criteriaTotal.from(baseModelClass)));

        Long total = entityManager.createQuery(criteriaTotal).getSingleResult();

        List<BaseModel> list = this.entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(
                this.pageable.getPageNumber() > 1
                    ? (this.pageable.getPageNumber() - 1) * this.pageable.getPageSize()
                    : 0
            )
            .setMaxResults(this.pageable.getPageSize())
            .getResultList();

        return new PaginationResult<BaseModel>(total, list);
    }
}
