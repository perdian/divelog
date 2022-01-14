package de.perdian.divelog.model.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.User;

@Repository
public interface DiveRepository extends JpaRepository<Dive, UUID> {

    long countDivesByUser(User user);

}
