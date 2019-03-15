package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ContactSF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactSF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactSFRepository extends JpaRepository<ContactSF, Long> {

}
