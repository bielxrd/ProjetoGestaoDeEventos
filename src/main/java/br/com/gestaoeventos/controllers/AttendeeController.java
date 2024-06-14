package br.com.gestaoeventos.controllers;

import br.com.gestaoeventos.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.gestaoeventos.dto.attendee.AttendeesListResponseDTO;
import br.com.gestaoeventos.services.AttendeeService;
import br.com.gestaoeventos.services.CheckInService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Participantes", description = "Rotas responsáveis pelo manuseio dos participantes.")
public class AttendeeController {

    @Autowired
    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    @Operation(summary = "Rota responsável por pegar o badge do participante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AttendeeBadgeResponseDTO.class))
            })
    })
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeBadgeResponseDTO attendeeBadge = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(attendeeBadge);
    }

    @GetMapping("/")
    @Operation(summary = "Rota responsável por trazer todos os participantes de todos os eventos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AttendeesListResponseDTO.class))
            })
    })
    public ResponseEntity<AttendeesListResponseDTO> getAllAttendees() {
        AttendeesListResponseDTO allAttendees = this.attendeeService.getAllAttendees();
        return ResponseEntity.ok(allAttendees);
    }

    @PostMapping("/{attendeeId}/check-in")
    @Operation(summary = "Rota responsável por realizar o checkIn do participante no evento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(example = "/atendees/{attendeeId}/badge"))
            })
    })
    public ResponseEntity doCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        this.attendeeService.checkInAttendee(attendeeId);

        URI uri = uriComponentsBuilder.path("/atendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
