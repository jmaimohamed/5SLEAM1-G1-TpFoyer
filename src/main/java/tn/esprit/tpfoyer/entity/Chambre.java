package tn.esprit.tpfoyer.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idChambre;

    @Column(unique = true)
    long numeroChambre;

    @Enumerated(EnumType.STRING)
    TypeChambre typeC;

    boolean disponible;

    public Chambre(Long numeroChambre, TypeChambre typeC, boolean disponible) {
        this.numeroChambre = numeroChambre;
        this.typeC = typeC;
        this.disponible = disponible;
    }

    @OneToMany
    Set<Reservation> reservations;

    @ManyToOne(cascade = CascadeType.ALL)
    Bloc bloc;

}
