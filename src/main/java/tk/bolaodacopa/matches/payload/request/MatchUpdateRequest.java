package tk.bolaodacopa.matches.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MatchUpdateRequest {


	@NotNull
	@Min(0)
	private Integer hometeamgoals;

	@NotNull
	@Min(0)
	private Integer awayteamgoals;

	@Size(max = 20)
	private String finishedmatch;


	public MatchUpdateRequest(@NotNull @Min(0) Integer hometeamgoals,
			@NotNull @Min(0) Integer awayteamgoals, @Size(max = 20) String finishedmatch) {
		super();
		this.hometeamgoals = hometeamgoals;
		this.awayteamgoals = awayteamgoals;
		this.finishedmatch = finishedmatch;
	}

	public Integer getHometeamgoals() {
		return hometeamgoals;
	}

	public void setHometeamgoals(Integer hometeamgoals) {
		this.hometeamgoals = hometeamgoals;
	}

	public Integer getAwayteamgoals() {
		return awayteamgoals;
	}

	public void setAwayteamgoals(Integer awayteamgoals) {
		this.awayteamgoals = awayteamgoals;
	}

	public String getFinishedmatch() {
		return finishedmatch;
	}

	public void setFinishedmatch(String finishedmatch) {
		this.finishedmatch = finishedmatch;
	}
		
}
