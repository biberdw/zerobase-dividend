package com.zerobase.dividend.web;

import com.zerobase.dividend.model.Auth;
import com.zerobase.dividend.model.MemberEntity;
import com.zerobase.dividend.security.TokenProvider;
import com.zerobase.dividend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Auth.SignUp request){
        //회원가입을 위한 API
        MemberEntity memberEntity = memberService.register(request);
        return ResponseEntity.ok(memberEntity);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request){
        MemberEntity member = memberService.authenticate(request);
        String token = tokenProvider.generateToken(member.getUsername(), member.getRoles());
        log.info("user login -> {}", request.getUsername());
        return ResponseEntity.ok(token);
    }


}
