import com.example.book.service.BookService;
import com.example.config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//设置类运行器
@RunWith(SpringJUnit4ClassRunner.class)
//设置Spring环境对应的配置类
@ContextConfiguration(classes = {SpringConfiguration.class}) //加载配置类
//@ContextConfiguration(locations={"classpath:applicationContext.xml"})//加载配置文件
public class BookTest {
    //支持自动装配注入bean
    @Autowired
    private BookService bookService;
    @Test
    public void testFindById(){
        System.out.println(bookService.getPageInfo(5, 1));
    }
    @Test
    public void testFindAll(){
        System.out.println(bookService.getAll());
    }
}