package ma.bankatispring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.bankatispring.model.enums.ERole;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class User implements Serializable {


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
