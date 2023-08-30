package uol.compass.postservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    private Integer id;
    private Integer userId;
    private String title;

    @Column(length = 500)
    private String body;

    @Enumerated(EnumType.STRING)
    private State state;
    private Boolean reprocessed = false;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<History> history = new ArrayList<>();

    public Post(Integer id, State state, List<History> history) {
        this.id = id;
        this.state = state;
        this.history = history;
    }
}
