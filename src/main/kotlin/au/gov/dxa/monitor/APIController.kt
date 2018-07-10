package au.gov.dxa.monitor

import au.gov.dxa.monitor.ingestion.Observation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class APIController {

    @Autowired
    private lateinit var repository: ConfigRepository

    @CrossOrigin
    @GetMapping("/look")
    fun look():Observation{
        val config = repository.findAll().first()
        val observation = Observation()
        observation.observe(config)
        return observation
    }
}