package br.com.gestaoeventos.repositories;

import br.com.gestaoeventos.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
