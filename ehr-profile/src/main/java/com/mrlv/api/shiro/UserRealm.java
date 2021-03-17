package com.mrlv.api.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mrlv.api.entity.SysUser;
import com.mrlv.api.service.ISysPermService;
import com.mrlv.api.service.ISysRoleService;
import com.mrlv.api.service.ISysUserService;
import com.mrlv.api.vo.AuthVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 这个类是参照JDBCRealm写的，主要是自定义了如何查询用户信息，如何查询用户的角色和权限，如何校验密码等逻辑
 */
public class UserRealm extends AuthorizingRealm  {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysPermService sysPermService;


    //适配密码
//    @Override
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        //设置用于匹配密码的CredentialsMatcher
//        HashedCredentialsMatcher hashcredentialsMatcher = new HashedCredentialsMatcher();
//        //采用算法：Md5Hash,Sha1Hash,Sha256Hash
//        hashcredentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
//        //是否采用16进制，默认是true
//        hashcredentialsMatcher.setStoredCredentialsHexEncoded(false);
//        //哈希值
//        hashcredentialsMatcher.setHashIterations(1024);
//        super.setCredentialsMatcher(hashcredentialsMatcher);
//    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        SysUser user = (SysUser) getAvailablePrincipal(principals);
        Set<AuthVo> roles = user.getRoles();
        Set<AuthVo> perms = user.getPerms();
        log.info("获取角色权限信息: roles: {}, perms: {}",roles,perms);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles.stream().map(AuthVo::getVal).collect(Collectors.toSet()));
        info.setStringPermissions(perms.stream().map(AuthVo::getVal).collect(Collectors.toSet()));
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        String username = userToken.getUsername();

        if (username == null){
            //账户问题发生异常，抛出此异常
            throw new AccountException("用户名不能为空");
        }

        SysUser user = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("login_name", username));
        if (user == null){
            //当用户不存在的时候，抛出此异常
            throw new UnknownAccountException("找不到用户(" + username + ")的账号信息");
        }
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        Set<AuthVo> roles = sysRoleService.getRolesByUserId(user.getId());   //用户所有角色值，用于shiro做角色权限的判断
        Set<AuthVo> perms = sysPermService.getPermsByUserId(user.getId());    //用户所有权限值，用于shiro做资源权限的判断
        user.getRoles().addAll(roles);
        user.getPerms().addAll(perms);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return info;
    }
}
