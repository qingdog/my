import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class HtmlSource {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        String url = "https://m.bqg18.cc/user/search.html?q=隐秘死角";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        // 设置User-Agent "Mozilla/5.0 (Windo.ws NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
        headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
//        headers.set(HttpHeaders.COOKIE, "goad=1; hm=83261a061cdd1e1a92ea094340452450"); // 添加cookie
//        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML)); // 设置响应类型为html
//        headers.set(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7,zh-TW;q=0.6");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        System.out.println(entity.getHeaders().toString());
        int num = 0;
        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] imageBytes = response.getBody();
            String imageString = new String(imageBytes);
//            new String(response.getBody(), "UTF-8");
            while ("1".equalsIgnoreCase(imageString)) {
                if (num == 100) return;
                imageString = new String(new RestTemplate().exchange(url, HttpMethod.GET, entity, byte[].class).getBody());
                System.out.println(++num);
            }
            System.out.println(imageString);
        } else {
            System.err.println(false);
        }
    }
}
