package ec.com.se.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import ec.com.se.domain.enumeration.Language;

/**
 * Entities                                                                    
 * 
 */
@ApiModel(description = ""
    + "Entities                                                               "
    + "")
@Entity
@Table(name = "category_lang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoryLang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language_code", nullable = false)
    private Language languageCode;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(Language languageCode) {
        this.languageCode = languageCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryLang categoryLang = (CategoryLang) o;
        if(categoryLang.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, categoryLang.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CategoryLang{" +
            "id=" + id +
            ", languageCode='" + languageCode + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
