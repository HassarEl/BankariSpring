package ma.bankatispring.web;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.bankatispring.dao.UserDao;
import ma.bankatispring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@AllArgsConstructor
public class UserController {
    private UserDao userDao;

    @GetMapping("/users")
    public String users(Model model,
                        @RequestParam(name="page",defaultValue = "0") int page,
                        @RequestParam(name="size",defaultValue = "5")int size,
                        @RequestParam(name="keyword",defaultValue = "") String keyword
    ){
        Page<User> pageUsers = userDao.findByFirstNameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listUsers", pageUsers.getContent());
        model.addAttribute("pages",      new int[pageUsers.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",    keyword);
        return "users";   // votre template listant les users
    }

    // — VUE DÉTAILLÉE —
    @GetMapping("/users/view")
    public String viewUser(@RequestParam Long id, Model model) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "users-view";
    }

    // — CHARGER LE FORMULAIRE D’EDITION —
    @GetMapping("/users/edit")
    public String editUser(@RequestParam Long id,
                           @RequestParam(name="page",    defaultValue="0") String page,
                           @RequestParam(name="keyword", defaultValue="")  String keyword,
                           Model model)
    {
        User user = userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user",    user);
        model.addAttribute("page",    page);
        model.addAttribute("keyword", keyword);
        return "users-edit";
    }
    // — FORMULAIRE « NOUVEL UTILISATEUR » —
    @GetMapping("/users/add")
    public String addUser(Model model) {
        // on envoie un User vide à Thymeleaf
        model.addAttribute("user", new User());
        // pour que la redirection après save ramène sur la première page
        model.addAttribute("page",    0);
        model.addAttribute("keyword", "");
        return "users-edit";
    }

    // — ENREGISTRER LA MODIFICATION —
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam(name="page")    String page,
                           @RequestParam(name="keyword") String keyword)
    {
        userDao.save(user);
        return "redirect:/users?page=" + page + "&keyword=" + keyword;
    }

    // — SUPPRESSION DÉJÀ PRÉSENTE —
    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page){
        userDao.deleteById(id);
        return "redirect:/users?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/users";
    }
}
