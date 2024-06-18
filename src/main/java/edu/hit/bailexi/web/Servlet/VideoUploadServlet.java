package edu.hit.bailexi.web.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hit.bailexi.domain.ResultInfo;
import edu.hit.bailexi.domain.Route;
import edu.hit.bailexi.service.RouteService;
import edu.hit.bailexi.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/upload")
@MultipartConfig
public class VideoUploadServlet extends HttpServlet {
    private RouteService routeService = new RouteServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        // 获取表单中<select>元素的值
        String selectedValue = request.getParameter("category");
        String name = request.getParameter("videoname");
        String intro = request.getParameter("introduction");
        String junzi = request.getParameter("heji");
        Route route = new Route();

        route.setRname(name);
        route.setRouteIntroduce(intro);
        route.setPrice(0);
        route.setRflag("1");
        route.setCid(Integer.parseInt(selectedValue));
        route.setSid(1);
        route.setJunzi(junzi);

        String pathname = String.valueOf(routeService.upload(route));

        // 获取上传的文件部分
        Part filePart = request.getPart("video");

        // 检查文件是否为空
        if (filePart != null && filePart.getSize() > 0) {
            // 获取文件名
//            String fileName = getSubmittedFileName(filePart);
            String fileName = pathname+".mp4";
            System.out.println("fileName");
            String saveDir = "D:\\WebProject\\";
//            Path filePath = Paths.get(saveDir + fileName);
            try (InputStream fileContent = filePart.getInputStream()/* 获取文件输入流 */) {
                Path filePath = Paths.get(saveDir + fileName);/* 定义目标文件路径 */;
                Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);


//                PosixFilePermission[] perms = PosixFilePermission.values();
//                Files.setPosixFilePermissions(filePath, Collections.singleton(PosixFilePermission.OTHERS_READ));


                System.out.println("File permissions set successfully.");




            } catch (IOException e) {
                // 处理可能发生的 IOException
                e.printStackTrace();
            }


//            response.setContentType("text/html");
//            response.getWriter().println("File uploaded successfully!");


            ResultInfo info = new ResultInfo();
            info.setFlag(true);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //将json数据写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);


        } else {
            // 设置响应内容类型和错误消息
            response.setContentType("text/html");
            response.getWriter().println("No file was uploaded or file size is zero.");
        }
    }

    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("Content-Disposition");
        String[] tokens = contentDisp.split(";");
        String fileName = "";
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                break;
            }
        }
        return fileName;
    }
}