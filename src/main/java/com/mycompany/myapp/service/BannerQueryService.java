package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Banner;
import com.mycompany.myapp.repository.BannerRepository;
import com.mycompany.myapp.service.criteria.BannerCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Banner} entities in the database.
 * The main input is a {@link BannerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Banner} or a {@link Page} of {@link Banner} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BannerQueryService extends QueryService<Banner> {

    private final Logger log = LoggerFactory.getLogger(BannerQueryService.class);

    private final BannerRepository bannerRepository;

    public BannerQueryService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    /**
     * Return a {@link List} of {@link Banner} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Banner> findByCriteria(BannerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Banner> specification = createSpecification(criteria);
        return bannerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Banner} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Banner> findByCriteria(BannerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Banner> specification = createSpecification(criteria);
        return bannerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BannerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Banner> specification = createSpecification(criteria);
        return bannerRepository.count(specification);
    }

    /**
     * Function to convert {@link BannerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Banner> createSpecification(BannerCriteria criteria) {
        Specification<Banner> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Banner_.id));
            }
            if (criteria.getImg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImg(), Banner_.img));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Banner_.url));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Banner_.title));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), Banner_.active));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), Banner_.position));
            }
            if (criteria.getStore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStore(), Banner_.store));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Banner_.type));
            }
            if (criteria.getHtml() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHtml(), Banner_.html));
            }
        }
        return specification;
    }
}
