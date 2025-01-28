package com.don.login_registration.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/**
 * This interface is a repository for the AppUser class which extends the JpaRepository
 * using the AppUser class and the Long type as the id
 * The @Repository annotation is used to indicate that the class provides
 * the mechanism for storage, retrieval, search, update and delete operation on objects.
 * The @Transactional annotation is used to indicate that the class is transactional
 * The @Modifying annotation is used to indicate that the method modifies the state of the database
 *
 * */
@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);



}
