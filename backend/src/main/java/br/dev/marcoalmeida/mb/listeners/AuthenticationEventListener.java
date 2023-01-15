package br.dev.marcoalmeida.mb.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEventListener {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) success.getSource();
        log.info(((User) token.getPrincipal()).getUsername());
    }
}
