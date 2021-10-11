package com.pluralsight.conferencedemo.controllers;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakersRepository;

    @GetMapping
    public List<Speaker> GetSpeakers(){
        return speakersRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker GetSpeakers(@PathVariable long id){
        return speakersRepository.getById(id);
    }

    @PostMapping
    public Speaker Create(@RequestBody final Speaker speaker){
        return speakersRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResponseEntity Delete(@PathVariable long id){

        if(speakersRepository.existsById(id)) {
            speakersRepository.deleteById(id);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
        var existingSpeaker = speakersRepository.getById(id);
        BeanUtils.copyProperties(speaker,existingSpeaker,"speaker_id");
        return speakersRepository.saveAndFlush(existingSpeaker);
    }
}
