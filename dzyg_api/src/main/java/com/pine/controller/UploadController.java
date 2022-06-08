package com.pine.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pine.miniapi.vo.ResultVO;

@RestController
@RequestMapping("upload")
public class UploadController {

	@PostMapping("fileUploadFile") 
	public ResultVO<Integer> fileUploadImg(MultipartFile file ) throws IOException {
		ResultVO<Integer> rs = new ResultVO<Integer>();
		String end = new Date().getTime() + file.getOriginalFilename();
		String pathName = "D:/file/" + end;
		File newFile = new File(pathName);
		file.transferTo(newFile);
		rs.addExtra("url", "http://localhost:8082/upload/showImage?fileName="+pathName);
		return rs;
	}
	 /**
     * 根据本地图片全路径，响应给浏览器1个图片流
     */
    @RequestMapping("/showImage")
    public void showImage(HttpServletResponse response, @RequestParam("fileName")String fileName) {
        show(response,fileName,"image");
    }
  
 
 
    public void  show(HttpServletResponse response, String fileName,String type){
        try{
            FileInputStream fis = new FileInputStream(fileName); // 以byte流的方式打开文件
            int i=fis.available(); //得到文件大小
            byte data[]=new byte[i];
            fis.read(data);  //读数据
            response.setContentType(type+"/*"); //设置返回的文件类型
            OutputStream toClient=response.getOutputStream(); //得到向客户端输出二进制数据的对象
            toClient.write(data);  //输出数据
            toClient.flush();
            toClient.close();
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("文件不存在");
        }
    }
 
  
	 
}
