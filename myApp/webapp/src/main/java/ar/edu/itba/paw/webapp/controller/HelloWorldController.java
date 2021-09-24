package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.webapp.forms.ContactForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
public class HelloWorldController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    EmailService emailService;

    @Autowired
    TeachesService teachesService;

    @Autowired
    ImageService imageService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("materias", subjectService.list());
        mav.addObject("greeting", userService.findById(1));
        return mav;
    }

    @RequestMapping(value = "/tutors", method = RequestMethod.GET, params = "query")
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery) {
        final ModelAndView mav = new ModelAndView("tutors");
        List<CardProfile> users = userService.filterUsers(searchQuery);
        mav.addObject("tutors", users);
        mav.addObject("materias", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays", Timetable.Days.values());
        return mav;
    }

    @RequestMapping(value = "/tutors", method = RequestMethod.GET, params = {"query", "price", "level"})
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery, @RequestParam(value = "price") @NotNull final String price,
                               @RequestParam(value = "level") @NotNull final String level) {
        final ModelAndView mav = new ModelAndView("tutors");
        List<CardProfile> users = userService.filterUsers(searchQuery, price, level);
        mav.addObject("tutors", users);
        mav.addObject("materias", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays", Timetable.Days.values());
        return mav;
    }

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.GET)
    public ModelAndView contactForm(@ModelAttribute("contactForm") final ContactForm form, @PathVariable("uid") final int uid) {
        final ModelAndView mav = new ModelAndView("contactForm");
        mav.addObject("user", userService.findById(uid));
        Optional<User> u = userService.getCurrentUser();
        if (u.isPresent()) {
            mav.addObject("present", 1);
        } else {
            mav.addObject("present", 0);
        }
        return mav;
    }


    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.POST)
    public ModelAndView contact(@PathVariable("uid") final int uid, @ModelAttribute("contactForm") @Valid final ContactForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form, uid);
        }
        User user = userService.findById(uid);
        Optional<User> u = userService.getCurrentUser();
        if (u.isPresent()) {
            emailService.sendTemplateMessage(user.getMail(), "GetAProff: Nueva petición de clase", u.get().getName(), form.getSubject(), u.get().getMail(), form.getMessage());
        } else {
            emailService.sendTemplateMessage(user.getMail(), "GetAProff: Nueva petición de clase", form.getName(), form.getSubject(), form.getEmail(), form.getMessage());
        }
        return new ModelAndView("redirect:/emailSent");
    }

    @RequestMapping("/emailSent")
    public ModelAndView emailSent() {
        final ModelAndView mav = new ModelAndView("emailSent");
        return mav;
    }

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final int uid) {
        Optional<User> u = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("profile");
        if (!u.isPresent()) {
            mav.addObject("edit", 0);
        } else {
            mav.addObject("edit", (u.get().getId() == uid) ? 1 : 0);
        }
        mav.addObject("user", userService.findById(uid));
        mav.addObject("timetable", userService.getUserSchedule(uid));
        mav.addObject("primaryLevel",userService.getUserSubjectsAndLevels(uid).get(1));
        mav.addObject("secondaryLevel",userService.getUserSubjectsAndLevels(uid).get(2));
        mav.addObject("tertiaryLevel",userService.getUserSubjectsAndLevels(uid).get(3));
        mav.addObject("noLevel",userService.getUserSubjectsAndLevels(uid).get(0));
        mav.addObject("image",getImage(uid));
        return mav;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        Optional<User> u = userService.getCurrentUser();
        if (!u.isPresent()) {
            return new ModelAndView("profile").addObject("edit", 0);
        }
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", u.get());
        mav.addObject("timetable", userService.getUserSchedule(u.get().getId()));
        mav.addObject("primaryLevel",userService.getUserSubjectsAndLevels(u.get().getId()).get(1));
        mav.addObject("secondaryLevel",userService.getUserSubjectsAndLevels(u.get().getId()).get(2));
        mav.addObject("tertiaryLevel",userService.getUserSubjectsAndLevels(u.get().getId()).get(3));
        mav.addObject("noLevel",userService.getUserSubjectsAndLevels(u.get().getId()).get(0));
        mav.addObject("image",getImage(u.get().getId()));
        mav.addObject("edit", 1);
        return mav;
    }

    @RequestMapping(value = "/profile", method= RequestMethod.POST)
    public ModelAndView profile( @ModelAttribute("scheduleInput") String schedule){
        userService.setUserSchedule(userService.getCurrentUser().get().getId(), schedule);
        return profile();
    }

    @RequestMapping(value = "image/${uid}", method = RequestMethod.GET)
    public String getImage(@PathVariable("uid") final int uid) {
        Optional<Image> image = imageService.findImageById(uid);
        if (!image.isPresent()) {
            return null;
        }
        String imgData = DatatypeConverter.printBase64Binary(image.get().getImage());
        String imgRef = "data:image/png;base64,";
        return imgRef.concat(imgData);
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
    public ModelAndView uploadFile(@ModelAttribute("img") final MultipartFile form) {

        return new ModelAndView("uploadFile");
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile imageFile) {
        Optional<User> u = userService.getCurrentUser();
        if (!u.isPresent()) {
            return "redirect:/login";
        }
        try {
            imageService.create(u.get().getId(), (int) imageFile.getSize(),imageFile.getBytes(),imageFile.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/profile";
    }


}
