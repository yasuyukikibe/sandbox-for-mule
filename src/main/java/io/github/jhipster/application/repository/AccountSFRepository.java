package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.AccountSF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountSF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountSFRepository extends JpaRepository<AccountSF, Long> {

}
