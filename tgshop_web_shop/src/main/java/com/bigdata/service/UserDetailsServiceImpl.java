package com.bigdata.service;

import com.bigdata.pojo.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caowei
 * @date 2018/10/21 21:27
 * @description
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用sellerService从数据库查询用户信息
        Seller seller = sellerService.findOne(username);
        if (seller != null && seller.getStatus().equals("1")) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
            //实际中返回的User的用户名和密码应该是从数据库中查询出来的,并且该User中还有包含其所具有的权限的集合
            return new User(username, seller.getPassword(), grantedAuths);
        }else {
            return null;
        }

    }


}