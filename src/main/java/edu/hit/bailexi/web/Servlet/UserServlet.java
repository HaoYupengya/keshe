package edu.hit.bailexi.web.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hit.bailexi.domain.*;
import edu.hit.bailexi.service.FavoriteService;
import edu.hit.bailexi.service.UserService;
import edu.hit.bailexi.service.impl.FavoriteServiceImpl;
import edu.hit.bailexi.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明userservice业务对象
    private UserService service = new UserServiceImpl();
    public FavoriteService favor_service = new FavoriteServiceImpl();

    //注册功能
    public void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service完成注册

        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //响应结果
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    //登录功能
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service查询

        User u = service.login(user);

        ResultInfo info = new ResultInfo();
        //判断用户对象是否为null
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //判断登录成功
        if (u != null) {
            //登录成功
            info.setFlag(true);
            HttpSession sessions = request.getSession();
            sessions.setAttribute("user", u);
        }

        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    //查询单个对象
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user = request.getSession().getAttribute("user");

        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    //退出功能
    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //销毁session
        request.getSession().invalidate();

        //跳转页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 查询用户收藏的所有旅游线路
     */
    public void myFavourite(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 获取当前登录用户
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return;
        }
        // 接收当前页码
        String currentPage_str = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        if(currentPage_str == null || currentPage_str.length() == 0){
            // currentPage为空，设置默认值为1，访问首页
            currentPage_str = "1";
        }
        int currentPage = Integer.parseInt(currentPage_str);

        int pageSize;//每页显示条数，不传递默认六条
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 6;
        }

        // 调用favoriteService中方法查询用户收藏的所有旅游线路,返回一个PageBean对象
        PageBean<Favorite> pageBean = favor_service.myFavorite(user, currentPage, pageSize);

        // 将pageBean对象序列化为json并响应给客户端
        writeValue(pageBean,response);

    }

    public void favouriteRank (HttpServletRequest request, HttpServletResponse response) throws IOException{
        int pageSize = 10;
        PageBean<Route> pageBean = favor_service.favouriteRank(pageSize);
        writeValue(pageBean,response);
    }

}