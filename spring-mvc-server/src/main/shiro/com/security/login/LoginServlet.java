/**   
* @Title: LoginServlet.java 
* @Package com.fan.security 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author weijunfan
* @date 2011-9-12 ����06:07:07 
* @version V1.0   
*/
package com.security.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        Subject subject= SecurityUtils.getSubject();
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        UsernamePasswordToken token=new UsernamePasswordToken();
        token.setUsername(username);
        token.setPassword(password.toCharArray());
        System.out.println("密码:"+String.valueOf(token.getPassword()));
        try{
            subject.login(token);
            response.sendRedirect("/user/index.jsp");
            return;
        }catch (IncorrectCredentialsException ice) {
            ice.printStackTrace();
        } catch (LockedAccountException lae) {
            lae.printStackTrace();
        }catch (AuthenticationException ae) {
            ae.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        response.sendRedirect("/index.jsp");
    }

}
