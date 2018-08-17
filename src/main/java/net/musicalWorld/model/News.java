package net.musicalWorld.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "descritpion")
    private String description;

    private String imgUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
