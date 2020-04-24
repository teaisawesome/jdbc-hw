package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main
{
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User user = User.builder()
                    .id((long) 5)
                    .name("James Bond")
                    .username("007")
                    .password("069")
                    .email("dao@dao.com")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1990-11-20"))
                    .enabled(true)
                    .build();

           dao.insert(user);
           dao.findById(5).get();
           dao.findByUsername("007").get();
           dao.delete(user);
           dao.list().forEach(System.out::println);
        }
    }
}
