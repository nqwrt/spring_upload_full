package edu.bit.ex;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import edu.bit.ex.service.FileService;

/**
 * Handles requests for the application home page.
 */

/*
��ü�� �̿��ؼ� �޴� ���(Ŀ�ǵ� ��ü), 
@RequestParam ������̼��� �̿��ϴ� ���,
MultipartHttpServletRequest �� �̿��ϴ� ���,

�� ���� ����� ���ϸ� �ȴ�.

���� �� ���� <input type="file" name="file" size="50">
������ ����������Ƽ �̸��� "file"�� ��ٸ�, 
���ε�� ���Ͽ� ���� ������Ƽ�� �׻� ������ �׹�! "file" �� �Ǿ�� �Ѵ�.
*/

	
@Controller
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
    FileService fileService;
    
/*    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ModelAndView getFilePage(ParamCollector inparams) {
        ModelAndView mav = new ModelAndView("fileUpload");
        return mav;
    }
    
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public ModelAndView getFile(ParamCollector inparams, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        if (log.isDebugEnabled()) {
            log.debug(inparams);
        }
        
        fileService.insertFileUpload(inparams.getMap(), request);
        return mav;
    }*/
	
	/*
    MultipartHttpServletRequest �������̽��� �������� �����ϴ� �������̽���,
    Multipart ��û�� ���� �� ���������� ���� HttpServletRequest ��� ���Ǵ� �������̽��̴�.
    MultipartHttpServletRequest �������̽��� ������ � �޼ҵ带 �����ϰ� ���� ������, HttpServletRequest �������̽��� 
    MultipartRequest �������̽��� ��ӹް� �ִ�. 
    MultipartHttpServletRequest �������̽��� javax.servelt.HttpSerletRequest �������̽��� ��ӹޱ� ������ �� ��û ������ ���ϱ� ����
    getPartmeter()�� getHeader()�� ���� �޼ҵ带 ����� �� ������, �߰��� MultipartRequest �������̽��� �����ϴ� Multipart���� �޼ҵ嵵 
    ����� �� �ִ�.



    -MultipartRequest �������̽��� ���� ���� �ֿ� �޼ҵ�
         Iterator<String> getFileNames() : ���ε� �� ���ϵ��� �̸� ����� �����ϴ� Iterator�� ���Ѵ�.
         MultipartFile getFile(String name) : �Ķ���� �̸��� name�� ���ε� ���� ������ ���Ѵ�.
         List<MultipartFile> getFiles(String name) : �Ķ���� �̸��� name�� ���ε� ���� ���� ����� ���Ѵ�.
         Map<String, MultipartFile> getFileMap() : �Ķ���� �̸��� Key��, �ش��ϴ� ���� ������ ������ ���Ѵ�.
	 */
	
	/*
	���ε��� ������ Ư�����Ϸ� �����ϰ� �ʹٸ� MultipartFile.transferTo() �� ���� ���ϴ�.
	File file = new File(filePath + fileName);

	multipartFile.transferTo(file); 
	 */
	@Resource(name="uploadPath")
	String uploadPath;
	
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public void upload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            
       	   List<MultipartFile> mf = request.getFiles("file");
       	   System.out.println(mf.size());
             		   
            //Map<String, MultipartFile> fileMap = request.getFileMap();
       	    //fileMap.values()
            for (MultipartFile multipartFile : mf) {
                
            	if(multipartFile.isEmpty() == false) {
            		
            		System.out.println("-------------file start----------");
            		System.out.println("name: " + multipartFile.getName());
            		System.out.println("file name: " + multipartFile.getOriginalFilename());
            		System.out.println("size: " + multipartFile.getSize());
            		System.out.println("-------------file end----------");            		
            		
            		uploadFile(multipartFile);            		
            		
            	}
            }
            
/*            response.setContentType("text;");
            response.getWriter().write(filePath);  */          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void uploadFile(MultipartFile multipartFile) {
    	
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + multipartFile.getOriginalFilename();
		String filePath = uploadPath + "\\" + savedName;
	
        File file = new File(filePath);
        
        if(!file.exists()) {
            file.mkdirs();
        }
        
        try {
			multipartFile.transferTo(file);
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
/*    public ModelAndView getFile(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request) throws Exception {
        
    	ModelAndView mav = new ModelAndView("jsonView");
        if (log.isDebugEnabled()) {
            log.debug(inparams);
        }
        
        fileService.insertFileUpload(inparams.getMap(), request);
        return mav;
    }*/
    
}
