package ma.bankatispring.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import ma.bankatispring.service.ConversionService;
import ma.bankatispring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/client/conversion")
@AllArgsConstructor
public class ClientConversionController {

    private final ConversionService conversionService;

    /**
     * Afficher le formulaire
     **/
    @GetMapping
    public String form(Model model,
                       HttpSession session,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("solde", user.getSolde());
        model.addAttribute("result", null);
        model.addAttribute("target", "");
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "client/conversion";
    }

    /**
     * Traiter la conversion
     **/
    @PostMapping
    public String convert(@RequestParam String target,
                          @RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "keyword", defaultValue = "") String keyword,
                          HttpSession session,
                          Model model) {
        User user = (User) session.getAttribute("user");
        BigDecimal res = conversionService.convert(user.getSolde(), target);

        model.addAttribute("solde", user.getSolde());
        model.addAttribute("result", res);
        model.addAttribute("target", target);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "client/conversion";
    }
}