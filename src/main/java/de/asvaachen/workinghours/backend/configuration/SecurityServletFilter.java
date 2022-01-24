package de.asvaachen.workinghours.backend.configuration;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class SecurityServletFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String Username = extractUsername(request);
        String token = extractToken(request);  // (1)

        if (isAuthenticated(token, Username) == false) {  // (2)
            // either no or wrong username/password
            // unfortunately the HTTP status code is called "unauthorized", instead of "unauthenticated"

            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED); // HTTP 417.
            return;
        }

        // allow the HttpRequest to go to Spring's DispatcherServlet
        // and @RestControllers/@Controllers.
        filterChain.doFilter(request, response); // (4)
    }


    private String extractUsername(HttpServletRequest request) {
        // Either try and read in a Basic Auth HTTP Header, which comes in the form of user:password
        // Or try and find form login request parameters or POST bodies, i.e. "username=me" & "password="myPass"

        Cookie[] tokens = request.getCookies();
        for (Cookie i: tokens){
            if (i.getName().equals("username")){
                String name = i.getValue();

                byte[] bytes = name.getBytes(StandardCharsets.ISO_8859_1);
                name = new String(bytes, StandardCharsets.UTF_8);

                return name;
            }
        }
        return "Missing User";
    }
    private String extractToken(HttpServletRequest request){
        // Either try and read in a Basic Auth HTTP Header, which comes in the form of user:password
        // Or try and find form login request parameters or POST bodies, i.e. "username=me" & "password="myPass"

        Cookie[] tokens = request.getCookies();
        for (Cookie i: tokens){
            if (i.getName().equals("token")){
                return i.getValue();
            }
        }
        return "Missing token";
    }

    private boolean isAuthenticated(String token, String Username) {
        // Check with Keycloak
        String Realm = System.getenv("KEYCLOAK_REALM");
        String Adresse = System.getenv("KEYCLOAK_URL");
        Boolean checkSSL = Objects.equals(System.getenv("CHECK_SSL"), "True");

        AtomicReference<Boolean> erg = new AtomicReference<>(false);

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Authorization", "Bearer " + token);

        Unirest.config().verifySsl(checkSSL);

        HttpResponse<JsonNode> response = Unirest
                .get( Adresse  + "/sso/auth/realms/" + Realm + "/protocol/openid-connect/userinfo")
                .headers(headers)
                .asJson()
                .ifSuccess(Httpresponse -> {
                    // Check if mail is Correct
                    if (Httpresponse.getBody().getObject().get("email").toString().equals(Username)){
                        erg.set(true);
                    }else{
                        System.out.println("error in Else");
                        System.out.println(Username + " is not " + Httpresponse.getBody().getObject().get("email").toString());
                        System.out.println(Httpresponse.getBody());
                        erg.set(false);
                    }
                })
                .ifFailure(Httpresponse -> {
                    // Status Failure
                    System.out.println("Error in failure");
                    System.out.println(Httpresponse.getBody());
                    System.out.println(Httpresponse.toString());
                    erg.set(false);
                });
        return erg.get();
    }

}