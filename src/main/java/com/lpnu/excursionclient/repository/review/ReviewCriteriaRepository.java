package com.lpnu.excursionclient.repository.review;

import com.lpnu.excursionclient.dto.PageDetails;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListSearchCriteria;
import com.lpnu.excursionclient.model.review.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReviewCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ReviewCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Review> findReviewsByExcursion(
            PageDetails pageDetails,
            GetExcursionReviewsListSearchCriteria getExcursionReviewsListSearchCriteria
    ) {
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = criteriaQuery.from(Review.class);
        Predicate predicate = getFindReviewsByExcursionPredicate(reviewRoot, getExcursionReviewsListSearchCriteria);
        criteriaQuery.where(predicate);
        setOrder(pageDetails, criteriaQuery, reviewRoot);

        TypedQuery<Review> typedQuery = getTypedQuery(criteriaQuery, pageDetails);

        Pageable pageable = getPageable(pageDetails);

        long reviewCount = getFindReviewsByExcursionCount(getExcursionReviewsListSearchCriteria);

        return new PageImpl<>(typedQuery.getResultList(), pageable, reviewCount);
    }

    private Predicate getFindReviewsByExcursionPredicate(
            Root<Review> reviewRoot,
            GetExcursionReviewsListSearchCriteria getExcursionReviewsListSearchCriteria
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(getExcursionReviewsListSearchCriteria.getStatus())) {
            predicates.add(criteriaBuilder.equal(reviewRoot.get("reviewStatus").get("name"),
                    getExcursionReviewsListSearchCriteria.getStatus()));
        }

        predicates.add(criteriaBuilder.equal(reviewRoot.get("excursion").get("id"),
                getExcursionReviewsListSearchCriteria.getExcursionId()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PageDetails pageDetails, CriteriaQuery<Review> criteriaQuery, Root<Review> reviewRoot) {
        if (pageDetails.getSortDirection().isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(reviewRoot.get(pageDetails.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(reviewRoot.get(pageDetails.getSortBy())));
        }
    }

    private TypedQuery<Review> getTypedQuery(CriteriaQuery<Review> criteriaQuery, PageDetails pageDetails) {
        TypedQuery<Review> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageDetails.getNumber() * pageDetails.getSize());
        typedQuery.setMaxResults(pageDetails.getSize());
        return typedQuery;
    }

    private Pageable getPageable(PageDetails pageDetails) {
        List<Sort.Order> orders = new ArrayList<>();

        orders.add(new Sort.Order(pageDetails.getSortDirection(), pageDetails.getSortBy()));

        Sort sort = Sort.by(orders);
        return PageRequest.of(pageDetails.getNumber(), pageDetails.getSize(), sort);
    }

    private long getFindReviewsByExcursionCount(
            GetExcursionReviewsListSearchCriteria getExcursionReviewsListSearchCriteria
    ) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Review> countRoot = countQuery.from(Review.class);
        Predicate predicate = getFindReviewsByExcursionPredicate(countRoot, getExcursionReviewsListSearchCriteria);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
