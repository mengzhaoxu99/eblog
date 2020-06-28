package com.mengzhaoxu.eblog.shiro;
import com.mengzhaoxu.eblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yixin
 * @date 2020/6/11 4:03 下午
 * @description
 */
@Component
public class AccountRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountProfile profile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        if (profile.getUsername().equals("admin")){
            SimpleAuthorizationInfo info  = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token =(UsernamePasswordToken) authenticationToken;
        AccountProfile accountProfile = userService.login(token.getUsername(),String.valueOf(token.getPassword()));
        SecurityUtils.getSubject().getSession().setAttribute("profile",accountProfile);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(accountProfile,token.getCredentials(),getName());
        return info;
    }
}
