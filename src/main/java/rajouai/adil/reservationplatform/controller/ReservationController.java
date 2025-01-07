package rajouai.adil.reservationplatform.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rajouai.adil.reservationplatform.dto.ReservationDto;
import rajouai.adil.reservationplatform.model.Reservation;
import rajouai.adil.reservationplatform.model.User;
import rajouai.adil.reservationplatform.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        return reservationService.getAllReservations(user);
    }

    @PostMapping("/return")
    public Reservation returnReservation(
            @RequestBody ReservationDto body
    ) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();

        return reservationService.returnReservation(user, body.reservationId());
    }

}