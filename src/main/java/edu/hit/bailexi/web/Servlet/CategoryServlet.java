package edu.hit.bailexi.web.Servlet;

import edu.hit.bailexi.domain.Category;
import edu.hit.bailexi.service.CategoryService;
import edu.hit.bailexi.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //调用service查询所有
        List<Category> cs = service.findAll();
        //序列化json返回
 /*        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),cs);*/
        writeValue(cs,response);
    }



}
