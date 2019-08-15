package camagru.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllUserNames() {
        List<String> usernameList = new ArrayList<>();

        usernameList.addAll(jdbcTemplate.queryForList("SELECT USERNAME FROM users;", String.class));
        return (usernameList);
    }
}
