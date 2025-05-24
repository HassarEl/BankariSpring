package ma.bankatispring.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import ma.bankatispring.dao.UserDao;
import ma.bankatispring.model.User;
import ma.bankatispring.model.enums.ERole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserDao userDao;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        // authentification basique : chercher par username puis vérifier password
        User user = userDao.findByUsername(username)
                .orElse(null);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Utilisateur ou mot de passe invalide");
            return "login";
        }
        // garder l’utilisateur en session
        session.setAttribute("user", user);
        // rediriger selon le rôle
        if (user.getRole() == ERole.ADMIN) {
            return "redirect:/credits";      // page admin crédits
        } else {
            return "redirect:/client/credits"; // page client crédits
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "profile";
    }
}
