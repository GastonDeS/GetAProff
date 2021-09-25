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
import java.util.ArrayList;
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

    private String[] sections = {"subjects", "schedule"};

    @Autowired
    ImageService imageService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        Optional<User> curr = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("index")
                .addObject("materias", subjectService.list())
                .addObject("greeting", userService.findById(1));
        if (!curr.isPresent() || curr.get().getUserRole() == 0){
            return mav;
        }
        return mav.addObject("user", curr.get());
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
    public ModelAndView contact(@PathVariable("uid") final int uid, @ModelAttribute("contactForm") @Valid final ContactForm form,
                                final BindingResult errors) {
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

    @RequestMapping("/profile/{uid}/{section}")
    public ModelAndView profile(@PathVariable("uid") final int uid, @PathVariable("section") final String section) {
        Optional<User> curr = userService.getCurrentUser();
        User user = userService.findById(uid);
        final ModelAndView mav = new ModelAndView("profile")
                .addObject("user", user)
                .addObject("timetable", userService.getUserSchedule(uid))
                .addObject("section", section)
                .addObject("sections", sections)
                .addObject("description", userService.getUserDescription(uid));
        if (!curr.isPresent() || curr.get().getUserRole() == 0) {
            mav.addObject("edit", 0);
        } else {
            mav.addObject("edit", 1);
        }
        List<Teaches> teachesList;
        List<SubjectInfo> subjectsGiven = new ArrayList<>();
        switch (section) {
            case "subjects":
                teachesList = teachesService.getSubjectListByUser(uid);
                for(Teaches t : teachesList) {
                    String name = subjectService.findById(t.getSubjectId()).get().getName();
                    subjectsGiven.add(new SubjectInfo(t.getSubjectId(), name, t.getPrice(), t.getLevel()));
                }
                mav.addObject("subjects", subjectsGiven);
                break;
        }
        return mav;
    }

//    @RequestMapping(value = "/profile", method = RequestMethod.POST)
//    public ModelAndView schedule(@ModelAttribute("scheduleInput") String schedule){
//        System.out.println(schedule);
//        return profile("schedule");
//    }

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
    public ModelAndView uploadFile() {

        return new ModelAndView("uploadFile");
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile imageFile) {
        Optional<User> u = userService.getCurrentUser();
        if (!u.isPresent()) {
            return "redirect:/login";
        }
        try {
            imageService.create(u.get().getId(),imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/profile";
    }


}
