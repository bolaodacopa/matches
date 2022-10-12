package tk.bolaodacopa.matches.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tk.bolaodacopa.matches.models.Match;
import tk.bolaodacopa.matches.payload.request.MatchCreateRequest;
import tk.bolaodacopa.matches.payload.request.MatchUpdateRequest;
import tk.bolaodacopa.matches.services.MatchService;

@RestController
@RequestMapping(path = "/api/matches")
public class MatchController {

	private final MatchService service;

	@Autowired
	public MatchController(MatchService service) {
		super();
		this.service = service;
	}	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<Match> addAll(@RequestHeader("x-token-subject") String sub, 
			@RequestBody @NotEmpty(message = "Lista de partidas não pode ser nula.") List<@Valid MatchCreateRequest> matches) {
		return this.service.addAll(matches);
	}
	
    @PutMapping(path = {"/{matchcode}"})
    @ResponseStatus(HttpStatus.OK)
    public Match updateOneMatch(@RequestHeader("x-token-subject") String sub,
    		@PathVariable("matchcode") String matchcode,
    		@RequestBody @NotEmpty(message = "Partida não pode ser nula.") @Valid MatchUpdateRequest match) {
    	return this.service.updateMatch(matchcode, match);
    }
    		
	@GetMapping(path = {"/{matchcode}"})
	@ResponseStatus(HttpStatus.OK)
	public Match findByFifacode(@RequestHeader("x-token-subject") String sub, 
			@PathVariable String matchcode) {
		return this.service.getByMatchcode(matchcode);
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Match> findAllByRequestParam(@RequestHeader("x-token-subject") String sub, 
			@RequestParam Map<String,String> allParams) {
		return this.service.findAllByRequestParam(allParams);
	}

}
