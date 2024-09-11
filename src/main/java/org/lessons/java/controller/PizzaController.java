package org.lessons.java.controller;

import java.util.List;

import org.lessons.java.model.Pizza;
import org.lessons.java.repo.PizzaRepository;
import org.lessons.java.service.OffertaSpecialeService;
import org.lessons.java.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {
	
	@Autowired
    private PizzaService pizzaService;

    @Autowired
    private OffertaSpecialeService offertaSpecialeService;
	
	//READ
	
	@GetMapping()
    public String index(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Pizza> pizze;
        if (search != null && !search.isEmpty()) {
            pizze = pizzaService.findByName(search);
        } else {
            pizze = pizzaService.findAll();  // Recupera tutte le pizze
        }
        
        model.addAttribute("pizze", pizze);
        model.addAttribute("search", search);  // Mantieni il valore di ricerca nel modello
        
        return "/pizze/index";
    }
	
	//READ PIZZE ORDER
	@GetMapping("/index-order")
    public String indexOrder(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Pizza> pizze;
        if (search != null && !search.isEmpty()) {
            pizze = pizzaService.findByName(search);
        } else {
            pizze = pizzaService.findAll();  // Recupera tutte le pizze
        }
        
        model.addAttribute("pizze", pizze);
        model.addAttribute("search", search);  // Mantieni il valore di ricerca nel modello
        
        return "/pizze/index-order";
    }

	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("pizza", pizzaService.findById(id));
		model.addAttribute("offerteSpeciali", pizza.getOfferteSpeciali());
		return "/pizze/show";
	}
	
	//SHOW PIZZE ORDINE
	@GetMapping("/index-order/{id}")
	public String showOrder(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("pizza", pizzaService.findById(id));
		return "/pizze/show-order";
	}
	
	//CREATE
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		return "/pizze/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza formPizza ,BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors()) {
			return "/pizze/create";
		}
		pizzaService.save(formPizza);
		
		return "redirect:/pizze";
	}
	
	//UPDATE
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("pizza", pizzaService.findById(id));
		
		return "/pizze/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza formPizza ,BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors()) {
			return "/pizze/edit";
		}
		pizzaService.save(formPizza);
		
		return "redirect:/pizze";
	}
	
	//DELETE
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes)
	{
		pizzaService.deleteById(id);
		
		attributes.addFlashAttribute("successMessage", "Pizza con id " + id + " è stata eliminata");
		
		return "redirect:/pizze";
	}
	
	

}
