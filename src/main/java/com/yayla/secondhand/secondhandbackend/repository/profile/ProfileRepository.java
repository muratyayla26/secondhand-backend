package com.yayla.secondhand.secondhandbackend.repository.profile;

import com.yayla.secondhand.secondhandbackend.model.entity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Boolean existsByAccountId(Long accountId);

    Optional<Profile> findByAccountId(Long accountId);
}
