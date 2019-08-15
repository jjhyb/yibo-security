package com.yibo.web.controller;


import com.yibo.dal.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 18:52
 * @Description:
 */

@RestController
@RequestMapping("/file")
public class FileController {

    private static final String folder = "E:\\Micro-service\\yibo-security\\yibo-security-demo\\src\\main\\java\\com\\yibo\\web\\controller";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(folder,new Date().getTime()+".txt");
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream = new FileInputStream(new File(folder,id+".txt"));
            outputStream = response.getOutputStream();
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        }catch (Exception e){
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
