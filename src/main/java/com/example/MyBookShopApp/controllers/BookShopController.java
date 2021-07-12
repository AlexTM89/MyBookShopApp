package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.AuthorService;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/bookshop")
public class BookShopController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookShopController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("bookData", bookService.getBookData());
        return "index";
    }

    @GetMapping("/genres")
    public String genresPage(Model model) {
        return "genres/index";
    }


    @GetMapping("/authors")
    public String authorsPage(Model model) {
        model.addAttribute("letters", authorService.getAuthorLetters());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors/index";
    }

    @GetMapping("/authors/byletter/{letter}")
    public String authorsPage(@PathVariable("letter") String letter,
                              Model model) {
        model.addAttribute("letters", authorService.getAuthorLetters());
        model.addAttribute("authors", authorService.getAuthorByLetter(letter));
        return "authors/index";
    }
}