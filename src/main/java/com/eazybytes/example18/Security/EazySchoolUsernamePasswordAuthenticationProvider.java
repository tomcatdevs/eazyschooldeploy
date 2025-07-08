package com.eazybytes.example18.Security;

import com.eazybytes.example18.Model.Person;
import com.eazybytes.example18.Model.Roles;
import com.eazybytes.example18.Repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
//@Profile("prod")
public class EazySchoolUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String passwordEntered=authentication.getCredentials().toString();
        Person person=personRepo.readByEmail(authentication.getName());
        if (null != person && person.getPersonId()>0 && passwordEncoder.matches(passwordEntered,person.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username,null,getGrantedAuthorities(person.getRoles()));
        }
        throw  new BadCredentialsException("BAD_CREDENTIAL_ENTERED");
    }

    private ArrayList<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        ArrayList<GrantedAuthority> getGrantedAuthorities=new ArrayList<>();
        getGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName().toUpperCase()));
        return getGrantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
