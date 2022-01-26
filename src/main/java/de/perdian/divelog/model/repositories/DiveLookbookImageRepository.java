package de.perdian.divelog.model.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import de.perdian.divelog.model.entities.DiveLogbookImage;

@Repository
public interface DiveLookbookImageRepository extends JpaRepository<DiveLogbookImage, UUID>, JpaSpecificationExecutor<DiveLogbookImage> {

}
