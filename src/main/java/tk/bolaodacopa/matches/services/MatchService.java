package tk.bolaodacopa.matches.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.bolaodacopa.matches.models.Stage;
import tk.bolaodacopa.matches.models.Match;
import tk.bolaodacopa.matches.models.Team;
import tk.bolaodacopa.matches.payload.request.MatchCreateRequest;
import tk.bolaodacopa.matches.payload.request.MatchUpdateRequest;
import tk.bolaodacopa.matches.repository.MatchRepository;
import tk.bolaodacopa.matches.repository.StageRepository;
import tk.bolaodacopa.matches.repository.TeamRepository;

@Service
public class MatchService {

	private final List<String> stages = Arrays.asList("GRUPOS","OITAVASFINAL","QUARTASFINAL","SEMIFINAL","TERCEIRO","FINAL");
	private final List<String> groups = Arrays.asList("A","B","C","D","E","F","G","H");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	private final TeamRepository teamRepository;
	private final MatchRepository matchRepository;
	private final StageRepository stageRepository;

	@Autowired
	public MatchService(MatchRepository matchRepository, 
			TeamRepository teamRepository,
			StageRepository stageRepository) {
		this.matchRepository = matchRepository;
		this.teamRepository = teamRepository;
		this.stageRepository = stageRepository;
	}

	public List<Match> addAll(List<MatchCreateRequest> matches) {
		Date date;
		List<Match> listMatches = new ArrayList<Match>();

		for(MatchCreateRequest request: matches) {
			if(matchRepository.findByMatchcode(request.getMatchcode()).isPresent())
				throw new RuntimeException("Erro: Já existe uma partida cadastrada com o código: " + request.getMatchcode());

			if(!(stages.contains(request.getStage())))
				throw new RuntimeException("Erro: Indique uma fase válida para a partida: " + request.getMatchcode());

			Stage stage = stageRepository.findByName(request.getStage())
					.orElseThrow(() -> new RuntimeException("Erro: Fase não encontrada: " + request.getStage()));

			if("SIM".equals(stage.getFinishedbets()))
				throw new RuntimeException("Erro: Fase finalizada para apostas: " + stage.getName());

			if("GRUPOS".equals(request.getStage())) {
				if(!(groups.contains(request.getMatchgroup())))
					throw new RuntimeException("Erro: Indique um grupo para a partida: " + request.getMatchcode());
			} else {
				request.setMatchgroup(null);
			}

			Team hometeam = teamRepository.findByFifacode(request.getFifacodehometeam())
					.orElseThrow(() -> new RuntimeException("Erro: Time da casa da partida não encontrado: " + request.getMatchcode()));

			Team awayteam = teamRepository.findByFifacode(request.getFifacodeawayteam())
					.orElseThrow(() -> new RuntimeException("Erro: Time visitante da partida não encontrado: " + request.getMatchcode()));			

			try {
				date = dateFormat.parse(request.getDate());
			} catch(Exception e) {
				throw new RuntimeException("Erro: Data da partida inválida (DD/MM/YYYY): " + request.getMatchcode());
			}

			Match match = new Match(request.getMatchcode(), stage, request.getMatchgroup(), 
					date, request.getRound(), hometeam, awayteam);

			listMatches.add(match);
		}

		return matchRepository.saveAll(listMatches);
	}

	public Match getByMatchcode(String matchcode) {
		Match match = matchRepository.findByMatchcode(matchcode)
				.orElseThrow(() -> new RuntimeException("Partida não encontrada: " + matchcode));		
		return match;		
	}

	public Match updateMatch(String matchcode, MatchUpdateRequest matchUpdateRequest) {
		String hometeamresult;
		String awayteamresult;
		String finishedmatch;

		Match match = matchRepository.findByMatchcode(matchcode)
				.orElseThrow(() -> new RuntimeException("Partida não encontrada: " + matchcode));			

		if (matchUpdateRequest.getHometeamgoals() > matchUpdateRequest.getAwayteamgoals()) {
			hometeamresult = "VITORIA";
			awayteamresult = "DERROTA";			
		} else if (matchUpdateRequest.getHometeamgoals() < matchUpdateRequest.getAwayteamgoals()) {
			hometeamresult = "DERROTA";
			awayteamresult = "VITORIA";			
		} else {
			hometeamresult = "EMPATE";
			awayteamresult = "EMPATE";			
		}

		if("SIM".equals(matchUpdateRequest.getFinishedmatch()))
			finishedmatch = "SIM";
		else
			finishedmatch = null;

		match.setHometeamgoals(matchUpdateRequest.getHometeamgoals());
		match.setAwayteamgoals(matchUpdateRequest.getAwayteamgoals());
		match.setHometeamresult(hometeamresult);
		match.setAwayteamresult(awayteamresult);
		match.setFinishedmatch(finishedmatch);

		return matchRepository.save(match);
	}

	public List<Match> findAllByRequestParam(Map<String, String> allParams) {
		List<Match> matches = new ArrayList<Match>();
		String stage = allParams.getOrDefault("stage", null);
		String group = allParams.getOrDefault("group", null);

		if((stage != null) && (group != null)) {
			matches = matchRepository.findAllByMatchgroupAndStageName(group, stage);
		} else if((stage != null)) {
			matches = matchRepository.findAllByStageName(stage);
		} else if((group != null)) {
			matches = matchRepository.findAllByMatchgroup(group);
		} else {
			matches = matchRepository.findAll();
		}

		return matches;
	}

}
