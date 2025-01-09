package rajouai.adil.reservationplatform.reservation;

import jakarta.persistence.*;
import rajouai.adil.reservationplatform.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean returned;
    private Date reservedUntil;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationItem> items;

    public List<ReservationItem> getItems() {
        if (items == null) {
            items = new ArrayList<>(List.of());
        }
        return new ArrayList<>(items);
    }

    public void setItems(List<ReservationItem> items) {
        this.items = items;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(Date reservedUntil) {
        this.reservedUntil = reservedUntil;
    }

    public Long getId() {
        return id;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean b) {
        this.returned = b;
    }
}
