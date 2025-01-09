package rajouai.adil.reservationplatform.reservation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import rajouai.adil.reservationplatform.common.Constants;
import rajouai.adil.reservationplatform.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_USER);
        return reservationService.getAllReservations(user);
    }

    @PostMapping("/return")
    public Reservation returnReservation(
            HttpServletRequest request,
            @RequestBody ReservationDto body
    ) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_USER);
        return reservationService.returnReservation(user, body.reservationId());
    }

}