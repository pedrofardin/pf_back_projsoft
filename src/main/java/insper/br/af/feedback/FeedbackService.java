package insper.br.af.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback save(Feedback feedback) {
        feedback.setId(UUID.randomUUID().toString());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> list(String email, List<String> roles) {
        if (roles.contains("ADMIN")) {
            return feedbackRepository.findAll();
        }

        return feedbackRepository.findByEmail(email);
    }

    public void delete(String id) {
        Feedback feedback = feedbackRepository.findById(id).get();
        feedbackRepository.delete(feedback);
    }
}
