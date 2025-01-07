package rajouai.adil.reservationplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rajouai.adil.reservationplatform.model.SlimCategoryProjection;
import rajouai.adil.reservationplatform.service.CategoryService;

import java.util.List;

@Controller
public class FrontendController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
}
