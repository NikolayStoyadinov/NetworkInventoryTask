package group.networkinventorytask.company.inventory.Exception;

import group.networkinventorytask.company.inventory.Exception.Grouped.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CardException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateCard(CardException ex) {return ex.getMessage();}

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

    @ExceptionHandler(RouterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleRouterException(RouterException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ShelfException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleShelfException(ShelfException ex) {
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

    @ExceptionHandler(SlotNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSlotNotFound(SlotNotFound ex) {return ex.getMessage();}

    @ExceptionHandler(RouterNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRouterNotFound(RouterNotFound ex) {return ex.getMessage();}

    @ExceptionHandler(SiteNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSiteNotFound(SiteNotFound ex) {return ex.getMessage();}

    @ExceptionHandler(UserBadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserRequest(UserBadRequest ex) {return ex.getMessage();}

    @ExceptionHandler(UserConflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserConflict(UserConflict ex) {return ex.getMessage();}

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserNotFound(UserNotFound ex) {return ex.getMessage();}
}