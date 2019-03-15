package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ContactSF.
 */
@Entity
@Table(name = "contact_sf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactSF implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "custom_ext_id_field", nullable = false)
    private String customExtIdField;

    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne
    @JsonIgnoreProperties("contactSFS")
    private AccountSF accountSF;

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

    public ContactSF customExtIdField(String customExtIdField) {
        this.customExtIdField = customExtIdField;
        return this;
    }

    public void setCustomExtIdField(String customExtIdField) {
        this.customExtIdField = customExtIdField;
    }

    public String getFirstName() {
        return firstName;
    }

    public ContactSF firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ContactSF lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AccountSF getAccountSF() {
        return accountSF;
    }

    public ContactSF accountSF(AccountSF accountSF) {
        this.accountSF = accountSF;
        return this;
    }

    public void setAccountSF(AccountSF accountSF) {
        this.accountSF = accountSF;
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
        ContactSF contactSF = (ContactSF) o;
        if (contactSF.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactSF.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactSF{" +
            "id=" + getId() +
            ", customExtIdField='" + getCustomExtIdField() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
