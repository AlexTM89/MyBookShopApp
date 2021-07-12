package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAuthorLetters() {
        return jdbcTemplate.query(
                "select distinct substr(a.surname, 1, 1) as letter from authors a order by 1",
                (ResultSet rs, int rowNum) -> rs.getString(1)
        );
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = jdbcTemplate.query("select * from authors order by id", (ResultSet rs, int rowNum) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setName(rs.getString("name"));
            author.setSurname(rs.getString("surname"));
            return author;
        });
        return new ArrayList<>(authors);
    }

    public List<Author> getAuthorByLetter(String letter) {
        List<Author> authors = jdbcTemplate.query("select * from authors a where substr(a.surname, 1, 1) = ?", (ResultSet rs, int rowNum) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setName(rs.getString("name"));
            author.setSurname(rs.getString("surname"));

            return author;
        }, letter);
        return new ArrayList<>(authors);
    }
}
