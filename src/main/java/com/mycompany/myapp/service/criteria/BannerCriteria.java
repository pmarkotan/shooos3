package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Banner} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.BannerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /banners?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BannerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter img;

    private StringFilter url;

    private StringFilter title;

    private StringFilter active;

    private StringFilter position;

    private StringFilter store;

    private StringFilter type;

    private StringFilter html;

    public BannerCriteria() {}

    public BannerCriteria(BannerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.img = other.img == null ? null : other.img.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.store = other.store == null ? null : other.store.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.html = other.html == null ? null : other.html.copy();
    }

    @Override
    public BannerCriteria copy() {
        return new BannerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getImg() {
        return img;
    }

    public StringFilter img() {
        if (img == null) {
            img = new StringFilter();
        }
        return img;
    }

    public void setImg(StringFilter img) {
        this.img = img;
    }

    public StringFilter getUrl() {
        return url;
    }

    public StringFilter url() {
        if (url == null) {
            url = new StringFilter();
        }
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getActive() {
        return active;
    }

    public StringFilter active() {
        if (active == null) {
            active = new StringFilter();
        }
        return active;
    }

    public void setActive(StringFilter active) {
        this.active = active;
    }

    public StringFilter getPosition() {
        return position;
    }

    public StringFilter position() {
        if (position == null) {
            position = new StringFilter();
        }
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public StringFilter getStore() {
        return store;
    }

    public StringFilter store() {
        if (store == null) {
            store = new StringFilter();
        }
        return store;
    }

    public void setStore(StringFilter store) {
        this.store = store;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getHtml() {
        return html;
    }

    public StringFilter html() {
        if (html == null) {
            html = new StringFilter();
        }
        return html;
    }

    public void setHtml(StringFilter html) {
        this.html = html;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BannerCriteria that = (BannerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(img, that.img) &&
            Objects.equals(url, that.url) &&
            Objects.equals(title, that.title) &&
            Objects.equals(active, that.active) &&
            Objects.equals(position, that.position) &&
            Objects.equals(store, that.store) &&
            Objects.equals(type, that.type) &&
            Objects.equals(html, that.html)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, img, url, title, active, position, store, type, html);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (img != null ? "img=" + img + ", " : "") +
            (url != null ? "url=" + url + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (position != null ? "position=" + position + ", " : "") +
            (store != null ? "store=" + store + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (html != null ? "html=" + html + ", " : "") +
            "}";
    }
}
