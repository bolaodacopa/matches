package tk.bolaodacopa.matches.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tk.bolaodacopa.matches.models.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {	
	
	Optional<Match> findByMatchcode(String matchcode);

}
