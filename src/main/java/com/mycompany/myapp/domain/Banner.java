package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Banner.
 */
@Entity
@Table(name = "banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "img", length = 255, nullable = false)
    private String img;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @NotNull
    @Size(max = 255)
    @Column(name = "active", length = 255, nullable = false)
    private String active;

    @NotNull
    @Size(max = 255)
    @Column(name = "position", length = 255, nullable = false)
    private String position;

    @NotNull
    @Size(max = 255)
    @Column(name = "store", length = 255, nullable = false)
    private String store;

    @NotNull
    @Size(max = 255)
    @Column(name = "type", length = 255, nullable = false)
    private String type;

    @Size(max = 255)
    @Column(name = "html", length = 255)
    private String html;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Banner id(Long id) {
        this.id = id;
        return this;
    }

    public String getImg() {
        return this.img;
    }

    public Banner img(String img) {
        this.img = img;
        return this;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return this.url;
    }

    public Banner url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public Banner title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActive() {
        return this.active;
    }

    public Banner active(String active) {
        this.active = active;
        return this;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPosition() {
        return this.position;
    }

    public Banner position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStore() {
        return this.store;
    }

    public Banner store(String store) {
        this.store = store;
        return this;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getType() {
        return this.type;
    }

    public Banner type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHtml() {
        return this.html;
    }

    public Banner html(String html) {
        this.html = html;
        return this;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banner)) {
            return false;
        }
        return id != null && id.equals(((Banner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Banner{" +
            "id=" + getId() +
            ", img='" + getImg() + "'" +
            ", url='" + getUrl() + "'" +
            ", title='" + getTitle() + "'" +
            ", active='" + getActive() + "'" +
            ", position='" + getPosition() + "'" +
            ", store='" + getStore() + "'" +
            ", type='" + getType() + "'" +
            ", html='" + getHtml() + "'" +
            "}";
    }
}
