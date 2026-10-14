package edu.demo.voting;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    private final Map<String, String> candidates = new LinkedHashMap<>();
    private final Set<String> votedUsers = ConcurrentHashMap.newKeySet();
    private final Map<String, AtomicInteger> counts = new ConcurrentHashMap<>();

    public WebController() {
        candidates.put("c1", "Aarav Mehta");
        candidates.put("c2", "Priya Reddy");
        candidates.put("c3", "Kabir Shah");
        candidates.keySet().forEach(id -> counts.put(id, new AtomicInteger(0)));
    }

    @GetMapping("/") public String home(Model model) {
        model.addAttribute("candidateCount", candidates.size());
        model.addAttribute("totalVotes", counts.values().stream().mapToInt(AtomicInteger::get).sum());
        return "index";
    }

    @GetMapping("/login") public String login() { return "login"; }

    @GetMapping("/dashboard") public String dashboard(Authentication auth, Model model) {
        model.addAttribute("username", auth.getName());
        model.addAttribute("hasVoted", votedUsers.contains(auth.getName()));
        model.addAttribute("candidates", candidates);
        model.addAttribute("totalVotes", counts.values().stream().mapToInt(AtomicInteger::get).sum());
        return "dashboard";
    }

    @PostMapping("/vote/submit")
    public String submitVote(@RequestParam String candidateId, Authentication auth, Model model) {
        if (!candidates.containsKey(candidateId)) {
            model.addAttribute("message", "Please select a valid demo candidate.");
            return "vote-result";
        }
        if (!votedUsers.add(auth.getName())) {
            model.addAttribute("message", "A demo vote has already been recorded for this account.");
            return "vote-result";
        }
        counts.get(candidateId).incrementAndGet();
        model.addAttribute("message", "Your demo vote was recorded successfully.");
        model.addAttribute("receipt", UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
        return "vote-result";
    }

    @GetMapping("/admin/results") public String results(Model model) {
        model.addAttribute("candidates", candidates);
        Map<String, Integer> results = new LinkedHashMap<>();
        counts.forEach((id, count) -> results.put(id, count.get()));
        model.addAttribute("results", results);
        model.addAttribute("totalVotes", results.values().stream().mapToInt(Integer::intValue).sum());
        return "results";
    }

    @GetMapping("/about") public String about() { return "about"; }
}
