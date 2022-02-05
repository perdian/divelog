package de.perdian.divelog.web.modules.dives;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.DiveLogbookImage;
import de.perdian.divelog.model.entities.components.Air;
import de.perdian.divelog.model.entities.components.AirType;
import de.perdian.divelog.model.entities.components.PlaceAndTime;
import de.perdian.divelog.model.repositories.DiveLookbookImageRepository;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.support.authentication.DiveLogUserHolder;
import de.perdian.divelog.web.support.types.image.Image;

@Service
class DiveEditorService {

    private DiveRepository diveRepository = null;
    private DiveLookbookImageRepository diveLookbookImageRepository = null;
    private DiveLogUserHolder userHolder = null;

    public DiveEditor createDiveEditor(Dive diveEntity, Dive templateEntity) {
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
            diveEditor.setSpot(diveEntity.getSpot());
            diveEditor.setStart(diveEntity.getStart());
            diveEditor.setTotalTimeMinutes(diveEntity.getTotalTimeMinutes());
            diveEditor.setLogbookImage(new Image(diveEntity.getLogbookImage() == null ? null : diveEntity.getLogbookImage().getJpegBytes()));
        } else {
            if (templateEntity == null) {
                diveEditor.setAir(new Air(AirType.REGULAR));
                diveEditor.setStart(new PlaceAndTime(LocalDate.now(), null));
            } else {

                PlaceAndTime newStart = new PlaceAndTime();
                newStart.setDate(templateEntity.getStart().getDate());
                newStart.setLocation(templateEntity.getStart().getLocation());
                newStart.setType(templateEntity.getStart().getType());
                PlaceAndTime newEnd = new PlaceAndTime();
                newEnd.setLocation(templateEntity.getEnd() == null ? null : templateEntity.getEnd().getLocation());

                diveEditor.setAir(new Air(templateEntity.getAir() == null ? AirType.REGULAR : templateEntity.getAir().getType()));
                diveEditor.setBuddy(templateEntity.getBuddy());
                diveEditor.setEnd(newEnd);
                diveEditor.setEnvironment(templateEntity.getEnvironment());
                diveEditor.setEquipment(templateEntity.getEquipment());
                diveEditor.setOrganizer(templateEntity.getOrganizer());
                diveEditor.setStart(newStart);

            }
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
        newEntity.setUser(this.getUserHolder().getCurrentUser().getEntity());
        return this.updateDiveFromEditor(newEntity, diveEditor);
    }

    @Transactional
    public Dive deleteDiveEntity(Dive diveEntity) {
        this.getDiveRepository().delete(diveEntity);
        return diveEntity;
    }

    public Dive createDiveEntity(UUID diveEntityId, boolean includeDetails) {
        Specification<Dive> diveEntitySpecification = this.getUserHolder().getCurrentUser().specification(Dive.class).and(
            (root, query, criteriaBuilder) -> {
                if (includeDetails) {
                    root.fetch("logbookImage");
                }
                return criteriaBuilder.equal(root.get("id"), diveEntityId);
            }
        );
        return diveEntityId == null ? null : this.getDiveRepository().findOne(diveEntitySpecification).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Dive> createPreviousDives() {
        Specification<Dive> diveEntitySpecification = this.getUserHolder().getCurrentUser().specification(Dive.class);
        PageRequest diveEntityPageRequest = PageRequest.of(0, 5, Dive.sortWithNewestFirst());
        return this.getDiveRepository().findAll(diveEntitySpecification, diveEntityPageRequest).getContent();
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

    DiveLogUserHolder getUserHolder() {
        return this.userHolder;
    }
    @Autowired
    void setUserHolder(DiveLogUserHolder userHolder) {
        this.userHolder = userHolder;
    }

}
