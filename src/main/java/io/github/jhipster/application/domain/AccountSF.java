package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AccountSF.
 */
@Entity
@Table(name = "account_sf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountSF implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "custom_ext_id_field", nullable = false)
    private String customExtIdField;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "accountSF")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactSF> contactSFS = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomExtIdField() {
        return customExtIdField;
    }

    public AccountSF customExtIdField(String customExtIdField) {
        this.customExtIdField = customExtIdField;
        return this;
    }

    public void setCustomExtIdField(String customExtIdField) {
        this.customExtIdField = customExtIdField;
    }

    public String getName() {
        return name;
    }

    public AccountSF name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ContactSF> getContactSFS() {
        return contactSFS;
    }

    public AccountSF contactSFS(Set<ContactSF> contactSFS) {
        this.contactSFS = contactSFS;
        return this;
    }

    public AccountSF addContactSF(ContactSF contactSF) {
        this.contactSFS.add(contactSF);
        contactSF.setAccountSF(this);
        return this;
    }

    public AccountSF removeContactSF(ContactSF contactSF) {
        this.contactSFS.remove(contactSF);
        contactSF.setAccountSF(null);
        return this;
    }

    public void setContactSFS(Set<ContactSF> contactSFS) {
        this.contactSFS = contactSFS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountSF accountSF = (AccountSF) o;
        if (accountSF.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountSF.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountSF{" +
            "id=" + getId() +
            ", customExtIdField='" + getCustomExtIdField() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
