package ma.bankatispring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.bankatispring.model.enums.ERole;


@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class User {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Double solde;
    @Enumerated(EnumType.STRING)
    private ERole role;

}
