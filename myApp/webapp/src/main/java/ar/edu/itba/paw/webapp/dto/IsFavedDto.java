package ar.edu.itba.paw.webapp.dto;

public class IsFavedDto {
    Boolean isFaved;

    public static IsFavedDto createIsFavedDto(Boolean isFaved) {
        IsFavedDto isFavedDto = new IsFavedDto();
        isFavedDto.isFaved = isFaved;
        return isFavedDto;
    }

    public Boolean getFaved() {
        return isFaved;
    }

    public void setFaved(Boolean faved) {
        isFaved = faved;
    }
}
