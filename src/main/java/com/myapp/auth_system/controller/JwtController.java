package com.myapp.auth_system.controller;

import com.myapp.auth_system.service.JwtService;
import com.myapp.auth_system.entity.JwtRequest;
import com.myapp.auth_system.entity.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        try {
            String jwtToken = jwtService.createJwtToken(jwtRequest.getUserName(),jwtRequest.getUserPassword());
            return ResponseEntity.ok(new JwtResponse(jwtToken, "Token generated successfully"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("login/logout")
    public ResponseEntity<String> login( HttpServletRequest request) {
        String token = extractTokenFromRequest(request);

    //This adds the token to the list of invalidated tokens
        Boolean logout=jwtService.invalidateToken(token);

        if(logout){
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.ok("Unable to logout");
    }
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
