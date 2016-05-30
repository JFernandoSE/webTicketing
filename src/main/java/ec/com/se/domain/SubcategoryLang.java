package ec.com.se.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import ec.com.se.domain.enumeration.Language;

/**
 * A SubcategoryLang.
 */
@Entity
@Table(name = "subcategory_lang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubcategoryLang implements Serializable {

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
    private Subcategory subcategory;

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

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubcategoryLang subcategoryLang = (SubcategoryLang) o;
        if(subcategoryLang.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subcategoryLang.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubcategoryLang{" +
            "id=" + id +
            ", languageCode='" + languageCode + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
