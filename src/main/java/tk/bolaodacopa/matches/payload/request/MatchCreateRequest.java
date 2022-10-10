package tk.bolaodacopa.matches.payload.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MatchCreateRequest {

	@NotBlank
	@Size(max = 20)
	private String matchcode;

	@NotBlank
	@Size(max = 20)
	private String stage;

	@Size(max = 20)
	private String matchgroup;

	@NotBlank
	private String date;	

	@NotNull
	@Max(8)
	@Min(1)
	private Integer round;

	@NotBlank
	@Size(max = 20)
	private String fifacodehometeam;	

	@NotBlank
	@Size(max = 20)
	private String fifacodeawayteam;


	public MatchCreateRequest() {

	}

	public MatchCreateRequest(@NotBlank @Size(max = 20) String matchcode, @NotBlank @Size(max = 20) String stage,
			@Size(max = 20) String matchgroup, @NotBlank String date, @NotNull @Max(8) @Min(1) Integer round,
			@NotBlank @Size(max = 20) String fifacodehometeam, @NotBlank @Size(max = 20) String fifacodeawayteam) {
		super();
		this.matchcode = matchcode;
		this.stage = stage;
		this.matchgroup = matchgroup;
		this.date = date;
		this.round = round;
		this.fifacodehometeam = fifacodehometeam;
		this.fifacodeawayteam = fifacodeawayteam;
	}

	public String getMatchcode() {
		return matchcode;
	}

	public void setMatchcode(String matchcode) {
		this.matchcode = matchcode;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getMatchgroup() {
		return matchgroup;
	}

	public void setMatchgroup(String matchgroup) {
		this.matchgroup = matchgroup;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public String getFifacodehometeam() {
		return fifacodehometeam;
	}

	public void setFifacodehometeam(String fifacodehometeam) {
		this.fifacodehometeam = fifacodehometeam;
	}

	public String getFifacodeawayteam() {
		return fifacodeawayteam;
	}

	public void setFifacodeawayteam(String fifacodeawayteam) {
		this.fifacodeawayteam = fifacodeawayteam;
	}	





}
