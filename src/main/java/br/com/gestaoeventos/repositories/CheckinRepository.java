package br.com.gestaoeventos.repositories;

import br.com.gestaoeventos.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<CheckIn, Integer> {
}
