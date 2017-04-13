package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.ForumUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
// ^ very important ^  
// import javax.activation.DataSource;
// ^ original ^  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author B
 */
@Repository
public class ForumUserRepositoryImpl implements ForumUserRepository {

    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final String SQL_INSERT_USER
            = "insert into users (username, password) values (?, ?)";
    private static final String SQL_INSERT_ROLE
            = "insert into user_roles (username, role) values (?, ?)";
    private static final String SQL_SELECT_ALL_USER
            = "select username, password from users";
    private static final String SQL_SELECT_ROLES
            = "select username, role from user_roles where username = ?";

    private static final String SQL_SELECT_USER
            = "select username, password from users where username = ?";

    private static final String SQL_DELETE_USER
            = "delete from users where username = ?";
    private static final String SQL_DELETE_ROLES
            = "delete from user_roles where username = ?";

    private static final class UserRowMapper
            implements RowMapper<ForumUser> {

        @Override
        public ForumUser mapRow(ResultSet rs, int i) throws SQLException {
            ForumUser user = new ForumUser();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    @Override
    public void create(ForumUser user) {
        jdbcOp.update(SQL_INSERT_USER,
                user.getUsername(),
                user.getPassword());
        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE,
                    user.getUsername(),
                    role);
        }
    }

    @Override
    public List<ForumUser> findAll() {
        List<ForumUser> users = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_USER);
        for (Map<String, Object> row : rows) {
            ForumUser user = new ForumUser();
            String username = (String) row.get("username");
            user.setUsername(username);
            user.setPassword((String) row.get("password"));
            List<Map<String, Object>> roleRows
                    = jdbcOp.queryForList(SQL_SELECT_ROLES, username);
            for (Map<String, Object> roleRow : roleRows) {
                user.addRole((String) roleRow.get("role"));
            }
            users.add(user);
        }
        return users;
    }

    @Override
    public ForumUser findByUsername(String username) {
        ForumUser user = jdbcOp.queryForObject(SQL_SELECT_USER,
                new UserRowMapper(), username);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ROLES,
                username);
        for (Map<String, Object> row : rows) {
            user.addRole((String) row.get("role"));
        }
        return user;

    }

    @Override
    public void deleteByUsername(String username) {
        jdbcOp.update(SQL_DELETE_ROLES, username);
        jdbcOp.update(SQL_DELETE_USER, username);

    }

}
