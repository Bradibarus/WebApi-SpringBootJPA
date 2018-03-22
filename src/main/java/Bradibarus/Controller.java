package Bradibarus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Controller {

    private final EntryRepository entryRepository;

    @Autowired
    Controller(EntryRepository entryRepository){
        this.entryRepository = entryRepository;
    }

    @RequestMapping(method = RequestMethod.POST, consumes={"application/json", "application/xml"})
    public long putEntry(@RequestBody Entry entry){
        Entry result = entryRepository.save(entry);
        return result.getId();
    }
}
