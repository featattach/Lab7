package lvov.lab7.controller;// DisciplineController.java
import lombok.extern.slf4j.Slf4j;
import lvov.lab7.dao.DisciplineRepository;
import lvov.lab7.entity.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/disciplines")
@Slf4j
public class DisciplineController {
    private final DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineController(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @GetMapping({"/list", "/",""})
    public ModelAndView getAllDisciplines() {
        ModelAndView mav = new ModelAndView("list-disciplines");
        mav.addObject("disciplines", disciplineRepository.findAll());
        return mav;
    }

    @GetMapping("/addDisciplineForm")
    public ModelAndView addDisciplineForm() {
        ModelAndView mav = new ModelAndView("addDisciplineForm");
        Discipline discipline = new Discipline();
        mav.addObject("discipline", discipline);
        return mav;
    }

    @PostMapping("/save")
    public RedirectView saveDiscipline(@ModelAttribute Discipline discipline) {
        disciplineRepository.save(discipline);
        return new RedirectView("/disciplines/list");
    }

    @PostMapping("/showUpdateForm")
    public ModelAndView showUpdateDisciplineForm(@RequestParam Long disciplineId) {
        ModelAndView mav = new ModelAndView("addDisciplineForm");
        Optional<Discipline> optionalDiscipline = disciplineRepository.findById(disciplineId);
        Discipline discipline = new Discipline();
        if (optionalDiscipline.isPresent()) {
            discipline = optionalDiscipline.get();
        }
        mav.addObject("discipline", discipline);
        return mav;
    }

    @GetMapping("/delete")
    public RedirectView deleteDiscipline(@RequestParam Long disciplineId) {
        disciplineRepository.deleteById(disciplineId);
        return new RedirectView("/disciplines/list");
    }
}
