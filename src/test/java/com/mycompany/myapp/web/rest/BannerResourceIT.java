package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Banner;
import com.mycompany.myapp.repository.BannerRepository;
import com.mycompany.myapp.service.criteria.BannerCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BannerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BannerResourceIT {

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_STORE = "AAAAAAAAAA";
    private static final String UPDATED_STORE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_HTML = "AAAAAAAAAA";
    private static final String UPDATED_HTML = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBannerMockMvc;

    private Banner banner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banner createEntity(EntityManager em) {
        Banner banner = new Banner()
            .img(DEFAULT_IMG)
            .url(DEFAULT_URL)
            .title(DEFAULT_TITLE)
            .active(DEFAULT_ACTIVE)
            .position(DEFAULT_POSITION)
            .store(DEFAULT_STORE)
            .type(DEFAULT_TYPE)
            .html(DEFAULT_HTML);
        return banner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banner createUpdatedEntity(EntityManager em) {
        Banner banner = new Banner()
            .img(UPDATED_IMG)
            .url(UPDATED_URL)
            .title(UPDATED_TITLE)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION)
            .store(UPDATED_STORE)
            .type(UPDATED_TYPE)
            .html(UPDATED_HTML);
        return banner;
    }

    @BeforeEach
    public void initTest() {
        banner = createEntity(em);
    }

    @Test
    @Transactional
    void createBanner() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();
        // Create the Banner
        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate + 1);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testBanner.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testBanner.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBanner.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBanner.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testBanner.getStore()).isEqualTo(DEFAULT_STORE);
        assertThat(testBanner.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBanner.getHtml()).isEqualTo(DEFAULT_HTML);
    }

    @Test
    @Transactional
    void createBannerWithExistingId() throws Exception {
        // Create the Banner with an existing ID
        banner.setId(1L);

        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkImgIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setImg(null);

        // Create the Banner, which fails.

        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setUrl(null);

        // Create the Banner, which fails.

        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setTitle(null);

        // Create the Banner, which fails.

        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setActive(null);

        // Create the Banner, which fails.

        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setPosition(null);

        // Create the Banner, which fails.

        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setStore(null);

        // Create the Banner, which fails.

        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setType(null);

        // Create the Banner, which fails.

        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBanners() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList
        restBannerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].store").value(hasItem(DEFAULT_STORE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].html").value(hasItem(DEFAULT_HTML)));
    }

    @Test
    @Transactional
    void getBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get the banner
        restBannerMockMvc
            .perform(get(ENTITY_API_URL_ID, banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banner.getId().intValue()))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.store").value(DEFAULT_STORE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.html").value(DEFAULT_HTML));
    }

    @Test
    @Transactional
    void getBannersByIdFiltering() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        Long id = banner.getId();

        defaultBannerShouldBeFound("id.equals=" + id);
        defaultBannerShouldNotBeFound("id.notEquals=" + id);

        defaultBannerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBannerShouldNotBeFound("id.greaterThan=" + id);

        defaultBannerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBannerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBannersByImgIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where img equals to DEFAULT_IMG
        defaultBannerShouldBeFound("img.equals=" + DEFAULT_IMG);

        // Get all the bannerList where img equals to UPDATED_IMG
        defaultBannerShouldNotBeFound("img.equals=" + UPDATED_IMG);
    }

    @Test
    @Transactional
    void getAllBannersByImgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where img not equals to DEFAULT_IMG
        defaultBannerShouldNotBeFound("img.notEquals=" + DEFAULT_IMG);

        // Get all the bannerList where img not equals to UPDATED_IMG
        defaultBannerShouldBeFound("img.notEquals=" + UPDATED_IMG);
    }

    @Test
    @Transactional
    void getAllBannersByImgIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where img in DEFAULT_IMG or UPDATED_IMG
        defaultBannerShouldBeFound("img.in=" + DEFAULT_IMG + "," + UPDATED_IMG);

        // Get all the bannerList where img equals to UPDATED_IMG
        defaultBannerShouldNotBeFound("img.in=" + UPDATED_IMG);
    }

    @Test
    @Transactional
    void getAllBannersByImgIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where img is not null
        defaultBannerShouldBeFound("img.specified=true");

        // Get all the bannerList where img is null
        defaultBannerShouldNotBeFound("img.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByImgContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where img contains DEFAULT_IMG
        defaultBannerShouldBeFound("img.contains=" + DEFAULT_IMG);

        // Get all the bannerList where img contains UPDATED_IMG
        defaultBannerShouldNotBeFound("img.contains=" + UPDATED_IMG);
    }

    @Test
    @Transactional
    void getAllBannersByImgNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where img does not contain DEFAULT_IMG
        defaultBannerShouldNotBeFound("img.doesNotContain=" + DEFAULT_IMG);

        // Get all the bannerList where img does not contain UPDATED_IMG
        defaultBannerShouldBeFound("img.doesNotContain=" + UPDATED_IMG);
    }

    @Test
    @Transactional
    void getAllBannersByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where url equals to DEFAULT_URL
        defaultBannerShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the bannerList where url equals to UPDATED_URL
        defaultBannerShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllBannersByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where url not equals to DEFAULT_URL
        defaultBannerShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the bannerList where url not equals to UPDATED_URL
        defaultBannerShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllBannersByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where url in DEFAULT_URL or UPDATED_URL
        defaultBannerShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the bannerList where url equals to UPDATED_URL
        defaultBannerShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllBannersByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where url is not null
        defaultBannerShouldBeFound("url.specified=true");

        // Get all the bannerList where url is null
        defaultBannerShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByUrlContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where url contains DEFAULT_URL
        defaultBannerShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the bannerList where url contains UPDATED_URL
        defaultBannerShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllBannersByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where url does not contain DEFAULT_URL
        defaultBannerShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the bannerList where url does not contain UPDATED_URL
        defaultBannerShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllBannersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where title equals to DEFAULT_TITLE
        defaultBannerShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bannerList where title equals to UPDATED_TITLE
        defaultBannerShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBannersByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where title not equals to DEFAULT_TITLE
        defaultBannerShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the bannerList where title not equals to UPDATED_TITLE
        defaultBannerShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBannersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBannerShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bannerList where title equals to UPDATED_TITLE
        defaultBannerShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBannersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where title is not null
        defaultBannerShouldBeFound("title.specified=true");

        // Get all the bannerList where title is null
        defaultBannerShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByTitleContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where title contains DEFAULT_TITLE
        defaultBannerShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the bannerList where title contains UPDATED_TITLE
        defaultBannerShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBannersByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where title does not contain DEFAULT_TITLE
        defaultBannerShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the bannerList where title does not contain UPDATED_TITLE
        defaultBannerShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBannersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where active equals to DEFAULT_ACTIVE
        defaultBannerShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the bannerList where active equals to UPDATED_ACTIVE
        defaultBannerShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBannersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where active not equals to DEFAULT_ACTIVE
        defaultBannerShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the bannerList where active not equals to UPDATED_ACTIVE
        defaultBannerShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBannersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultBannerShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the bannerList where active equals to UPDATED_ACTIVE
        defaultBannerShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBannersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where active is not null
        defaultBannerShouldBeFound("active.specified=true");

        // Get all the bannerList where active is null
        defaultBannerShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByActiveContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where active contains DEFAULT_ACTIVE
        defaultBannerShouldBeFound("active.contains=" + DEFAULT_ACTIVE);

        // Get all the bannerList where active contains UPDATED_ACTIVE
        defaultBannerShouldNotBeFound("active.contains=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBannersByActiveNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where active does not contain DEFAULT_ACTIVE
        defaultBannerShouldNotBeFound("active.doesNotContain=" + DEFAULT_ACTIVE);

        // Get all the bannerList where active does not contain UPDATED_ACTIVE
        defaultBannerShouldBeFound("active.doesNotContain=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBannersByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where position equals to DEFAULT_POSITION
        defaultBannerShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the bannerList where position equals to UPDATED_POSITION
        defaultBannerShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllBannersByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where position not equals to DEFAULT_POSITION
        defaultBannerShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the bannerList where position not equals to UPDATED_POSITION
        defaultBannerShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllBannersByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultBannerShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the bannerList where position equals to UPDATED_POSITION
        defaultBannerShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllBannersByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where position is not null
        defaultBannerShouldBeFound("position.specified=true");

        // Get all the bannerList where position is null
        defaultBannerShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByPositionContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where position contains DEFAULT_POSITION
        defaultBannerShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the bannerList where position contains UPDATED_POSITION
        defaultBannerShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllBannersByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where position does not contain DEFAULT_POSITION
        defaultBannerShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the bannerList where position does not contain UPDATED_POSITION
        defaultBannerShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllBannersByStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where store equals to DEFAULT_STORE
        defaultBannerShouldBeFound("store.equals=" + DEFAULT_STORE);

        // Get all the bannerList where store equals to UPDATED_STORE
        defaultBannerShouldNotBeFound("store.equals=" + UPDATED_STORE);
    }

    @Test
    @Transactional
    void getAllBannersByStoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where store not equals to DEFAULT_STORE
        defaultBannerShouldNotBeFound("store.notEquals=" + DEFAULT_STORE);

        // Get all the bannerList where store not equals to UPDATED_STORE
        defaultBannerShouldBeFound("store.notEquals=" + UPDATED_STORE);
    }

    @Test
    @Transactional
    void getAllBannersByStoreIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where store in DEFAULT_STORE or UPDATED_STORE
        defaultBannerShouldBeFound("store.in=" + DEFAULT_STORE + "," + UPDATED_STORE);

        // Get all the bannerList where store equals to UPDATED_STORE
        defaultBannerShouldNotBeFound("store.in=" + UPDATED_STORE);
    }

    @Test
    @Transactional
    void getAllBannersByStoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where store is not null
        defaultBannerShouldBeFound("store.specified=true");

        // Get all the bannerList where store is null
        defaultBannerShouldNotBeFound("store.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByStoreContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where store contains DEFAULT_STORE
        defaultBannerShouldBeFound("store.contains=" + DEFAULT_STORE);

        // Get all the bannerList where store contains UPDATED_STORE
        defaultBannerShouldNotBeFound("store.contains=" + UPDATED_STORE);
    }

    @Test
    @Transactional
    void getAllBannersByStoreNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where store does not contain DEFAULT_STORE
        defaultBannerShouldNotBeFound("store.doesNotContain=" + DEFAULT_STORE);

        // Get all the bannerList where store does not contain UPDATED_STORE
        defaultBannerShouldBeFound("store.doesNotContain=" + UPDATED_STORE);
    }

    @Test
    @Transactional
    void getAllBannersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where type equals to DEFAULT_TYPE
        defaultBannerShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the bannerList where type equals to UPDATED_TYPE
        defaultBannerShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllBannersByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where type not equals to DEFAULT_TYPE
        defaultBannerShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the bannerList where type not equals to UPDATED_TYPE
        defaultBannerShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllBannersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultBannerShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the bannerList where type equals to UPDATED_TYPE
        defaultBannerShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllBannersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where type is not null
        defaultBannerShouldBeFound("type.specified=true");

        // Get all the bannerList where type is null
        defaultBannerShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByTypeContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where type contains DEFAULT_TYPE
        defaultBannerShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the bannerList where type contains UPDATED_TYPE
        defaultBannerShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllBannersByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where type does not contain DEFAULT_TYPE
        defaultBannerShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the bannerList where type does not contain UPDATED_TYPE
        defaultBannerShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllBannersByHtmlIsEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where html equals to DEFAULT_HTML
        defaultBannerShouldBeFound("html.equals=" + DEFAULT_HTML);

        // Get all the bannerList where html equals to UPDATED_HTML
        defaultBannerShouldNotBeFound("html.equals=" + UPDATED_HTML);
    }

    @Test
    @Transactional
    void getAllBannersByHtmlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where html not equals to DEFAULT_HTML
        defaultBannerShouldNotBeFound("html.notEquals=" + DEFAULT_HTML);

        // Get all the bannerList where html not equals to UPDATED_HTML
        defaultBannerShouldBeFound("html.notEquals=" + UPDATED_HTML);
    }

    @Test
    @Transactional
    void getAllBannersByHtmlIsInShouldWork() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where html in DEFAULT_HTML or UPDATED_HTML
        defaultBannerShouldBeFound("html.in=" + DEFAULT_HTML + "," + UPDATED_HTML);

        // Get all the bannerList where html equals to UPDATED_HTML
        defaultBannerShouldNotBeFound("html.in=" + UPDATED_HTML);
    }

    @Test
    @Transactional
    void getAllBannersByHtmlIsNullOrNotNull() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where html is not null
        defaultBannerShouldBeFound("html.specified=true");

        // Get all the bannerList where html is null
        defaultBannerShouldNotBeFound("html.specified=false");
    }

    @Test
    @Transactional
    void getAllBannersByHtmlContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where html contains DEFAULT_HTML
        defaultBannerShouldBeFound("html.contains=" + DEFAULT_HTML);

        // Get all the bannerList where html contains UPDATED_HTML
        defaultBannerShouldNotBeFound("html.contains=" + UPDATED_HTML);
    }

    @Test
    @Transactional
    void getAllBannersByHtmlNotContainsSomething() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList where html does not contain DEFAULT_HTML
        defaultBannerShouldNotBeFound("html.doesNotContain=" + DEFAULT_HTML);

        // Get all the bannerList where html does not contain UPDATED_HTML
        defaultBannerShouldBeFound("html.doesNotContain=" + UPDATED_HTML);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBannerShouldBeFound(String filter) throws Exception {
        restBannerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].store").value(hasItem(DEFAULT_STORE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].html").value(hasItem(DEFAULT_HTML)));

        // Check, that the count call also returns 1
        restBannerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBannerShouldNotBeFound(String filter) throws Exception {
        restBannerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBannerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBanner() throws Exception {
        // Get the banner
        restBannerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner
        Banner updatedBanner = bannerRepository.findById(banner.getId()).get();
        // Disconnect from session so that the updates on updatedBanner are not directly saved in db
        em.detach(updatedBanner);
        updatedBanner
            .img(UPDATED_IMG)
            .url(UPDATED_URL)
            .title(UPDATED_TITLE)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION)
            .store(UPDATED_STORE)
            .type(UPDATED_TYPE)
            .html(UPDATED_HTML);

        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBanner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testBanner.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testBanner.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBanner.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBanner.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testBanner.getStore()).isEqualTo(UPDATED_STORE);
        assertThat(testBanner.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBanner.getHtml()).isEqualTo(UPDATED_HTML);
    }

    @Test
    @Transactional
    void putNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBannerWithPatch() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner using partial update
        Banner partialUpdatedBanner = new Banner();
        partialUpdatedBanner.setId(banner.getId());

        partialUpdatedBanner.position(UPDATED_POSITION);

        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testBanner.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testBanner.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBanner.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBanner.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testBanner.getStore()).isEqualTo(DEFAULT_STORE);
        assertThat(testBanner.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBanner.getHtml()).isEqualTo(DEFAULT_HTML);
    }

    @Test
    @Transactional
    void fullUpdateBannerWithPatch() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner using partial update
        Banner partialUpdatedBanner = new Banner();
        partialUpdatedBanner.setId(banner.getId());

        partialUpdatedBanner
            .img(UPDATED_IMG)
            .url(UPDATED_URL)
            .title(UPDATED_TITLE)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION)
            .store(UPDATED_STORE)
            .type(UPDATED_TYPE)
            .html(UPDATED_HTML);

        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testBanner.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testBanner.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBanner.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBanner.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testBanner.getStore()).isEqualTo(UPDATED_STORE);
        assertThat(testBanner.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBanner.getHtml()).isEqualTo(UPDATED_HTML);
    }

    @Test
    @Transactional
    void patchNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeDelete = bannerRepository.findAll().size();

        // Delete the banner
        restBannerMockMvc
            .perform(delete(ENTITY_API_URL_ID, banner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
