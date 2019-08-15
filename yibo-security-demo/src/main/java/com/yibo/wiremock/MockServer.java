package com.yibo.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 23:05
 * @Description:
 *
 * wireMock框架使用
 */
public class MockServer {

    public static void main(String[] args) throws IOException {
        WireMock.configureFor(8082);
        WireMock.removeAllMappings();
        mock("/user/1","01");
    }

    private static void mock(String url, String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/"+filename+".txt");
        String content = FileUtil.readAsString(resource.getFile());
        //下面这个和上面一行代码效果一样
        //String content1 = StringUtils.join(FileUtils.readLines(resource.getFile(),"UTF-8").toArray(),"\n");

        //get请求
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url))
                .willReturn(WireMock.aResponse()
                        //body里面写 json
                        .withBody(content)
                        //返回状态码
                        .withStatus(200)));
    }
}
