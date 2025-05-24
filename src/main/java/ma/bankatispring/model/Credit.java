package ma.bankatispring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.bankatispring.model.enums.Status;

@Entity
@NoArgsConstructor
@Data
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double mtCredit;
    private Long nbrMois;
    private String motif;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

