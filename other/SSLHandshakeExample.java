import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class SSLHandshakeExample {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        /*String url = "https://static5.hentai-cosplays.com/upload/20211208/249/254696/1.jpg";

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });*/

        String baseUrl = "https://static5.hentai-cosplays.com/upload/20211208/249/254696/";
        String fileExtension = ".jpg";
        int startIndex = 1;
        int endIndex = 63;
        String outputDirectory = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        if (!new File(outputDirectory).exists()){
            if (new File(outputDirectory).mkdir())
                throw new RuntimeException("创建目录出错：" + outputDirectory);
        }

        // 设置代理
        String proxyHost = "127.0.0.1";
        int proxyPort = 10809;
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);

//        RestTemplate restTemplate = new RestTemplate();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        for (int i = startIndex; i <= endIndex; i++) {
            String url = baseUrl + i + fileExtension;
            endIndex = 1;

            HttpHeaders headers = new HttpHeaders();
            // 设置User-Agent "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
            headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 12345");
//            headers.set(HttpHeaders.COOKIE, "your-cookie-value"); // 添加cookie
            headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG)); // 设置响应类型为image/jpeg
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] imageBytes = response.getBody();
                String fileName = i + fileExtension;
                String filePath = outputDirectory + fileName;

                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    outputStream.write(imageBytes);
                }
//                System.out.println("Downloaded " + fileName + " to " + System.getProperty("user.dir"));
                System.out.println("Downloaded " + fileName + " to " + System.getProperty("user.dir") + "\\" + filePath);
            }
        }
    }
}
