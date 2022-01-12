package de.asvaachen.workinghours.backend.configuration;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.catalina.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class SecurityServletFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String Username = extractUsername(request);
        String token = extractToken(request);  // (1)
        System.out.println("Filtering for: " + token);

        if (notAuthenticated(token, Username)) {  // (2)
            // either no or wrong username/password
            // unfortunately the HTTP status code is called "unauthorized", instead of "unauthenticated"
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401.
            return;
        }

        // allow the HttpRequest to go to Spring's DispatcherServlet
        // and @RestControllers/@Controllers.
        chain.doFilter(request, response); // (4)
    }

    private String extractUsername(HttpServletRequest request) {
        // Either try and read in a Basic Auth HTTP Header, which comes in the form of user:password
        // Or try and find form login request parameters or POST bodies, i.e. "username=me" & "password="myPass"

        Cookie[] tokens = request.getCookies();
        for (Cookie i: tokens){
            if (i.getName() == "username"){
                return i.getValue();
            }
        }
        return "";
    }
    private String extractToken(HttpServletRequest request){
        // Either try and read in a Basic Auth HTTP Header, which comes in the form of user:password
        // Or try and find form login request parameters or POST bodies, i.e. "username=me" & "password="myPass"

        Cookie[] tokens = request.getCookies();
        for (Cookie i: tokens){
            if (i.getName() == "token"){
                return i.getValue();
            }
        }
        return "";
    }

    private boolean notAuthenticated(String token, String Username) {
        // Check with Keycloak
        String Realm = System.getenv("KEYCLOAK_REALM");
        String Adresse = System.getenv("KEYCLOAK_URL");

        AtomicReference<Boolean> erg = new AtomicReference<>(false);

        HttpResponse<JsonNode> response = Unirest.get("http://" + Adresse + "/auth/realms/" + Realm + "/protocol/openid-connect/userinfo")
                .header("Authorization", token)
                .asJson()
                .ifSuccess(Httpresponse -> {
                    // Check if mail is Correct
                    if (Httpresponse.getBody().getObject().get("email").toString() == Username){
                        erg.set(true);
                    }else{
                        erg.set(false);
                    }
                })
                .ifFailure(Httpresponse -> {
                    // Status Failure
                    System.out.println(Httpresponse);
                    erg.set(false);
                });
        /*{
            "sub": "469131f0-063c-4982-954b-b12f437aed85",
                "email_verified": true,
                "name": "test test",
                "preferred_username": "test",
                "given_name": "test",
                "family_name": "test",
                "email": "test@babab"
        }*/

        return erg.get();
    }

}