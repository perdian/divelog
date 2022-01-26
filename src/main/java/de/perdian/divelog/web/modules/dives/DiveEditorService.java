package de.perdian.divelog.web.modules.dives;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.DiveLogbookImage;
import de.perdian.divelog.model.entities.components.PlaceAndTime;
import de.perdian.divelog.model.repositories.DiveLookbookImageRepository;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.support.authentication.DiveLogUser;
import de.perdian.divelog.web.support.types.image.Image;

@Service
class DiveEditorService {

    private DiveRepository diveRepository = null;
    private DiveLookbookImageRepository diveLookbookImageRepository = null;
    private DiveLogUser currentUser = null;

    public DiveEditor createDiveEditor(Dive diveEntity) {
        DiveEditor diveEditor = new DiveEditor();
        if (diveEntity != null) {
            diveEditor.setAir(diveEntity.getAir());
            diveEditor.setBuddy(diveEntity.getBuddy());
            diveEditor.setComments(diveEntity.getComments());
            diveEditor.setEnd(diveEntity.getEnd());
            diveEditor.setEnvironment(diveEntity.getEnvironment());
            diveEditor.setEquipment(diveEntity.getEquipment());
            diveEditor.setMaxDepth(diveEntity.getMaxDepth());
            diveEditor.setOrganizer(diveEntity.getOrganizer());
            diveEditor.setPadiStatistics(diveEntity.getPadiStatistics());
            diveEditor.setSpot(diveEntity.getSpot());
            diveEditor.setStart(diveEntity.getStart());
            diveEditor.setTotalTimeMinutes(diveEntity.getTotalTimeMinutes());
            diveEditor.setLogbookImage(new Image(diveEntity.getLogbookImage() == null ? null : diveEntity.getLogbookImage().getJpegBytes()));
        } else {
            diveEditor.setStart(new PlaceAndTime(LocalDate.now(), null));
        }
        return diveEditor;
    }

    @Transactional
    public Dive updateDiveFromEditor(Dive diveEntity, DiveEditor diveEditor) {
        diveEntity.setAir(diveEditor.getAir());
        diveEntity.setBuddy(diveEditor.getBuddy());
        diveEntity.setComments(diveEditor.getComments());
        diveEntity.setEnd(diveEditor.getEnd());
        diveEntity.setEnvironment(diveEditor.getEnvironment());
        diveEntity.setEquipment(diveEditor.getEquipment());
        diveEntity.setMaxDepth(diveEditor.getMaxDepth());
        diveEntity.setOrganizer(diveEditor.getOrganizer());
        diveEntity.setPadiStatistics(diveEditor.getPadiStatistics());
        diveEntity.setSpot(diveEditor.getSpot());
        diveEntity.setStart(diveEditor.getStart());
        diveEntity.setTotalTimeMinutes(diveEditor.getTotalTimeMinutes());
        if (diveEntity.getLogbookImage() == null) {
            DiveLogbookImage newLoogbookImage = new DiveLogbookImage();
            newLoogbookImage.setDive(diveEntity);
            diveEntity.setLogbookImage(newLoogbookImage);
        }
        Dive savedDiveEntity = this.getDiveRepository().save(diveEntity);

        if (diveEditor.isLogbookImageDelete()) {
            savedDiveEntity.getLogbookImage().setJpegBytes(null);
            this.getDiveLookbookImageRepository().save(savedDiveEntity.getLogbookImage());
        } else if (diveEditor.getLogbookImageUpload() != null && diveEditor.getLogbookImageUpload().isAvailable()) {
            savedDiveEntity.getLogbookImage().setJpegBytes(diveEditor.getLogbookImageUpload().getJpegBytes());
            this.getDiveLookbookImageRepository().save(savedDiveEntity.getLogbookImage());
        }

        return savedDiveEntity;
    }

    @Transactional
    public Dive createDiveFromEditor(DiveEditor diveEditor) {
        Dive newEntity = new Dive();
        newEntity.setUser(this.getCurrentUser().getUserEntity());
        return this.updateDiveFromEditor(newEntity, diveEditor);
    }

    public Dive createDiveEntity(UUID diveEntityId) {
        Specification<Dive> diveEntitySpecification = this.getCurrentUser().specification(Dive.class).and(
            (root, query, criteriaBuilder) -> {
                root.fetch("logbookImage");
                return criteriaBuilder.equal(root.get("id"), diveEntityId);
            }
        );
        return diveEntityId == null ? null : this.getDiveRepository().findOne(diveEntitySpecification).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    DiveRepository getDiveRepository() {
        return this.diveRepository;
    }
    @Autowired
    void setDiveRepository(DiveRepository diveRepository) {
        this.diveRepository = diveRepository;
    }

    DiveLookbookImageRepository getDiveLookbookImageRepository() {
        return this.diveLookbookImageRepository;
    }
    @Autowired
    void setDiveLookbookImageRepository(DiveLookbookImageRepository diveLookbookImageRepository) {
        this.diveLookbookImageRepository = diveLookbookImageRepository;
    }

    DiveLogUser getCurrentUser() {
        return this.currentUser;
    }
    @Autowired
    void setCurrentUser(DiveLogUser currentUser) {
        this.currentUser = currentUser;
    }

}
