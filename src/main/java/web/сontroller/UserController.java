package web.сontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Message;
import web.model.User;
import web.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.parser.Entity;

//import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String showUserList(ModelMap model) {
        model.addAttribute("listUsers", userService.getAll());
        return "index";
    }

    @GetMapping("/newuser")
    public String showAddForm(User user) {
        return "add_user";
    }


    @PostMapping("/save")
    public String addUser(Model model, @ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, User user, ModelMap model) {

        userService.save(user);
        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUsers(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({IllegalArgumentException.class})
    public ModelAndView error(IllegalArgumentException e) {
        ModelAndView modelAndView = new ModelAndView("error_page");
        Message message = new Message("Произошла ошибка сервера ...", e.getMessage());
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView error(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error_page");
        Message message = new Message("Похоже нам не удалось найти пользователя...", e.getMessage());
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
