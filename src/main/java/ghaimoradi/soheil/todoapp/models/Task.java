package ghaimoradi.soheil.todoapp.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime reminderDate;
    private boolean hasReminder;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}