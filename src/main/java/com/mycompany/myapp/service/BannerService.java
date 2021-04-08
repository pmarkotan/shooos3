package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Banner;
import com.mycompany.myapp.repository.BannerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Banner}.
 */
@Service
@Transactional
public class BannerService {

    private final Logger log = LoggerFactory.getLogger(BannerService.class);

    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    /**
     * Save a banner.
     *
     * @param banner the entity to save.
     * @return the persisted entity.
     */
    public Banner save(Banner banner) {
        log.debug("Request to save Banner : {}", banner);
        return bannerRepository.save(banner);
    }

    /**
     * Partially update a banner.
     *
     * @param banner the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Banner> partialUpdate(Banner banner) {
        log.debug("Request to partially update Banner : {}", banner);

        return bannerRepository
            .findById(banner.getId())
            .map(
                existingBanner -> {
                    if (banner.getImg() != null) {
                        existingBanner.setImg(banner.getImg());
                    }
                    if (banner.getUrl() != null) {
                        existingBanner.setUrl(banner.getUrl());
                    }
                    if (banner.getTitle() != null) {
                        existingBanner.setTitle(banner.getTitle());
                    }
                    if (banner.getActive() != null) {
                        existingBanner.setActive(banner.getActive());
                    }
                    if (banner.getPosition() != null) {
                        existingBanner.setPosition(banner.getPosition());
                    }
                    if (banner.getStore() != null) {
                        existingBanner.setStore(banner.getStore());
                    }
                    if (banner.getType() != null) {
                        existingBanner.setType(banner.getType());
                    }
                    if (banner.getHtml() != null) {
                        existingBanner.setHtml(banner.getHtml());
                    }

                    return existingBanner;
                }
            )
            .map(bannerRepository::save);
    }

    /**
     * Get all the banners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Banner> findAll(Pageable pageable) {
        log.debug("Request to get all Banners");
        return bannerRepository.findAll(pageable);
    }

    /**
     * Get one banner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Banner> findOne(Long id) {
        log.debug("Request to get Banner : {}", id);
        return bannerRepository.findById(id);
    }

    /**
     * Delete the banner by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Banner : {}", id);
        bannerRepository.deleteById(id);
    }
}
