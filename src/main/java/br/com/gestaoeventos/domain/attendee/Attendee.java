package br.com.gestaoeventos.domain.attendee;

import br.com.gestaoeventos.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "event_id" , nullable = true)
    private Event event;

    @Column(nullable = false, name = "created_at")
    @CreationTimestamp
    public LocalDateTime createdAt;
}
