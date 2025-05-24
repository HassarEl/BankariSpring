package ma.bankatispring.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import ma.bankatispring.dao.CreditDao;
import ma.bankatispring.model.Credit;
import ma.bankatispring.model.User;
import ma.bankatispring.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client/credits")
@AllArgsConstructor
public class ClientCreditController {

    private final CreditDao creditDao;

    /** 1. Lister ses demandes **/
    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(name="page",    defaultValue="0") int page,
                       @RequestParam(name="size",    defaultValue="5") int size,
                       @RequestParam(name="keyword", defaultValue="")  String keyword)
    {
        User user = (User) session.getAttribute("user");
        Page<Credit> p = creditDao.findByUserAndMotifContains(user, keyword, PageRequest.of(page, size));
        model.addAttribute("listCredits", p.getContent());
        model.addAttribute("pages",       new int[p.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",     keyword);
        return "client/credits";
    }

    /** 2. Formulaire de création **/
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("credit", new Credit());
        model.addAttribute("page",    0);
        model.addAttribute("keyword", "");
        return "client/credits-edit";
    }

    /** 3. Enregistrer une nouvelle demande **/
    @PostMapping("/save")
    public String save(@ModelAttribute Credit credit,
                       @RequestParam(name="page",    defaultValue="0") int page,
                       @RequestParam(name="keyword", defaultValue="")  String keyword,
                       HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        credit.setUser(user);
        credit.setStatus(Status.ENCOURS);
        creditDao.save(credit);
        return "redirect:/client/credits?page=" + page + "&keyword=" + keyword;
    }

    /** 4. Supprimer si encore ENCOURS **/
    @GetMapping("/delete")
    public String delete(@RequestParam Long id,
                         @RequestParam int page,
                         @RequestParam String keyword,
                         HttpSession session)
    {
        Credit c = creditDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        User user = (User) session.getAttribute("user");
        if (c.getUser().equals(user) && c.getStatus() == Status.ENCOURS) {
            creditDao.deleteById(id);
        }
        return "redirect:/client/credits?page=" + page + "&keyword=" + keyword;
    }
}
