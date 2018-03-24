package Bradibarus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(long id) {
        super("Could not find entry with id: " + id);
    }
}
