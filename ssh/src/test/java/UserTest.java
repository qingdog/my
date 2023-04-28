import com.example.book.dao.UserDao;
import com.example.book.dao.UserDaoImp;
import com.example.book.dao.UserDetailsDaoImp;
import com.example.book.domain.User;
import com.example.book.domain.UserDetails;
import com.example.book.service.BookService;
import com.example.book.service.UserService;
import com.example.book.service.impl.UserDetailsServiceImp;
import com.example.config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
public class UserTest {
    @Autowired
    private UserDetailsDaoImp userDetailsDaoImp;
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Autowired
    private UserService userService;

    @Test
    public void testFindAll() {
        List<UserDetails> list = userDetailsServiceImp.list();
        if (!CollectionUtils.isEmpty(list)) {
            User user = list.get(0).getUser();
            System.out.println(user);
        }


        System.out.println(list);
    }

    @Test
    public void testUserAll() {
        // 错误
//        List<UserDetails> list = userDetailsDaoImp.list();
//        if (!CollectionUtils.isEmpty(list)) {
//            User user = list.get(0).getUser();
//            System.out.println(user);
//        }
        List<User> list = userService.list(1,1);
//        for (User user : list) {
//            UserDetails details = user.getDetails();
//            if (details != null)
//                System.out.println(details);
//        }
        System.out.println(list);
    }

    @Test
    public void testSqlUserQuery() {
        User user = new User();
        user.setName("张三");
        List<User> list = userService.list(user);
        System.out.println(list);
    }
}