package tk.bolaodacopa.matches.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tk.bolaodacopa.matches.models.Stage;


@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
	
	Optional<Stage> findByName(String name);	

}
