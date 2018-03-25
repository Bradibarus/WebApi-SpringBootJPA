package Bradibarus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "api")
public class Controller {

    private final EntryRepository entryRepository;

    @Autowired
    Controller(EntryRepository entryRepository){
        this.entryRepository = entryRepository;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/xml", "application/json"})
    public UUID putEntry(@Valid @RequestBody Entry entry){
        return entryRepository.save(entry).getId();
    }

    @RequestMapping(method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public ResponseEntity<?> getEntry(@RequestParam("id") UUID uuid){
        return entryRepository.findById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }
}
