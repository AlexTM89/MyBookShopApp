package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            author.setFistName(rs.getString("name"));
            author.setLastName(rs.getString("surname"));
            return author;
        });
        return new ArrayList<>(authors);
    }

    public List<Author> getAuthorByLetter(String letter) {
        List<Author> authors = jdbcTemplate.query("select * from authors a where substr(a.surname, 1, 1) = ?", (ResultSet rs, int rowNum) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setFistName(rs.getString("name"));
            author.setLastName(rs.getString("surname"));

            return author;
        }, letter);
        return new ArrayList<>(authors);
    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = jdbcTemplate.query("select * from authors", (ResultSet rs, int rowNum) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setFistName(rs.getString("first_name"));
            author.setLastName(rs.getString("last_name"));

            return author;
        });
        Map<String, List<Author>> result = authors
                .stream()
                .collect(Collectors.groupingBy((a) -> a.getLastName().substring(0, 1)));
        return result;
    }
}
