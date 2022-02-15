package com.jpabook.jpashop.service.social;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
@Service
public class Auth2Service {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    /**
     * �׼��� ��ū���� ���̹����� ������ �ޱ�
     * @param accessToken
     * @return
     * @throws IOException
     */
    public String getProfileFromNaver(String accessToken) throws IOException {
      // ���̹� �α��� ���� ��ū;
      String apiURL = "https://openapi.naver.com/v1/nid/me";
      String headerStr = "Bearer " + accessToken; // Bearer ������ ���� �߰�
      String res = requestToServer(apiURL, headerStr);
      return res;
    }
    
    /**
     * ���� ��ȿȭ(�α׾ƿ�)
     * @param session
     * @return
     */
    @RequestMapping("/naver/invalidate")
    public String invalidateSession(HttpSession session) {
      session.invalidate();
      return "redirect:/naver";
    }
    
    /**
     * ���� ��� �޼ҵ�
     * @param apiURL
     * @return
     * @throws IOException
     */
    private String requestToServer(String apiURL) throws IOException {
      return requestToServer(apiURL, "");
    }
    
    /**
     * ���� ��� �޼ҵ�
     * @param apiURL
     * @param headerStr
     * @return
     * @throws IOException
     */
    private String requestToServer(String apiURL, String headerStr) throws IOException {
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      System.out.println("header Str: " + headerStr);
      if(headerStr != null && !headerStr.equals("") ) {
        con.setRequestProperty("Authorization", headerStr);
      }
      int responseCode = con.getResponseCode();
      BufferedReader br;
      System.out.println("responseCode="+responseCode);
      if(responseCode == 200) { // ���� ȣ��
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // ���� �߻�
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer res = new StringBuffer();
      while ((inputLine = br.readLine()) != null) {
        res.append(inputLine);
      }
      br.close();
      if(responseCode==200) {
        return res.toString();
      } else {
        return null;
      }
    }
}