package Bradibarus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
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
    public long putEntry(@RequestBody Entry entry){
        return entryRepository.save(entry).getId();
    }

    @RequestMapping(method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public ResponseEntity<?> getEntry(@RequestParam("id") long id){
        return entryRepository.findById(id).map((e)->{
                return ResponseEntity.ok(e);
        } ).orElse(ResponseEntity.notFound().build());
    }
}
