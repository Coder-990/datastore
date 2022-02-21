package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "racun", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RacunEntity {

    @Id
    @Column(name = "userid")
    private String userId;
    @Basic
    @Column(name = "password")
    private String password;
}
