package ghaimoradi.soheil.todoapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}