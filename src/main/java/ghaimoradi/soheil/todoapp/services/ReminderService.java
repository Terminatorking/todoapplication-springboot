package ghaimoradi.soheil.todoapp.services;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // This method will be executed every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void sendReminders() {
        System.out.println("--- Checking for reminders at " + LocalDateTime.now() + " ---");
        LocalDateTime now = LocalDateTime.now();
        // Using the corrected repository method name
        List<Task> tasksToRemind = taskRepository.findByReminderDateLessThanEqualAndReminderSentIsFalse(now);

        if (tasksToRemind.isEmpty()) {
            // System.out.println("No tasks to remind right now.");
            return; // Exit if no tasks are found
        }

        System.out.println("Found " + tasksToRemind.size() + " tasks to remind.");

        for (Task task : tasksToRemind) {
            try {
                System.out.println("Processing reminder for task ID: " + task.getId() + " - '" + task.getTitle() + "'");
                String destination = "/topic/reminders/" + task.getUser().getId();
                messagingTemplate.convertAndSend(destination, "Reminder: " + task.getTitle());
                System.out.println("Sent message to " + destination);

                task.setReminderSent(true);
                taskRepository.save(task);
                System.out.println("Task ID: " + task.getId() + " marked as sent.");
            } catch (Exception e) {
                System.err.println("Error processing reminder for task ID: " + task.getId() + " - " + e.getMessage());
            }
        }
    }
}
