package ma.bankatispring.web;

import lombok.AllArgsConstructor;
import ma.bankatispring.dao.CreditDao;
import ma.bankatispring.model.Credit;
import ma.bankatispring.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class CreditController {

    private final CreditDao creditDao;

    /** Liste paginée + recherche par motif **/
    @GetMapping("/credits")
    public String listCredits(Model model,
                              @RequestParam(name="page",    defaultValue="0") int page,
                              @RequestParam(name="size",    defaultValue="5") int size,
                              @RequestParam(name="keyword", defaultValue="")  String keyword)
    {
        Page<Credit> p = creditDao.findByMotifContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listCredits", p.getContent());
        model.addAttribute("pages",       new int[p.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",     keyword);
        return "credits";
    }

    /** Approuver une demande (ENCOURS → ACCEPT) **/
    @GetMapping("/credits/approve")
    public String approve(@RequestParam Long id,
                          @RequestParam int page,
                          @RequestParam String keyword)
    {
        Credit c = creditDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credit Id:" + id));
        c.setStatus(Status.ACCEPT);
        creditDao.save(c);
        return "redirect:/credits?page=" + page + "&keyword=" + keyword;
    }

    /** Refuser une demande (ENCOURS → REFUSE) **/
    @GetMapping("/credits/refuse")
    public String refuse(@RequestParam Long id,
                         @RequestParam int page,
                         @RequestParam String keyword)
    {
        Credit c = creditDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credit Id:" + id));
        c.setStatus(Status.REFUSE);
        creditDao.save(c);
        return "redirect:/credits?page=" + page + "&keyword=" + keyword;
    }
}
