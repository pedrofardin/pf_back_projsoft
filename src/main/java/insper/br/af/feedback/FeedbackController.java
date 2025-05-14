package insper.br.af.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedbacks")
    public List<Feedback> listFeedbacks(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("https://musica-insper.com/email");
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");

        return feedbackService.list(email, roles);
    }

    @PostMapping("/feedbacks")
    public Feedback createFeedback(@AuthenticationPrincipal Jwt jwt, @RequestBody Feedback feedback) {
        String email = jwt.getClaimAsString("https://musica-insper.com/email");
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");

        feedback.setEmail(email);
        return feedbackService.save(feedback);
    }

    @DeleteMapping("/feedbacks/{id}")
    public void deleteFeedback(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");

        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        feedbackService.delete(id);
    }
}
