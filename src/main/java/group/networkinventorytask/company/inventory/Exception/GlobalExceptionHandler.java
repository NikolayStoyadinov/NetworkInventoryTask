package group.networkinventorytask.company.inventory.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SiteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateSiteCode(SiteException ex) {return ex.getMessage();}

    @ExceptionHandler(SiteHasChildrenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleSiteHasChildren(SiteHasChildrenException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(SlotOccupiedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleSlotOccupied(SlotOccupiedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CardInstalledElsewhere.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCardInstalledElsewhere(CardInstalledElsewhere ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CardNotInSlot.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCardNotInSlot(CardNotInSlot ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ShelfNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleShelfNotFound(ShelfNotFound ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CardNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCardNotFound(CardNotFound ex) {
        return ex.getMessage();
    }
}