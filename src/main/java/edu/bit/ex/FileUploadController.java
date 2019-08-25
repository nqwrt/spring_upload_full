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
빈객체를 이용해서 받는 방법(커맨드 객체), 
@RequestParam 어노테이션을 이용하는 방법,
MultipartHttpServletRequest 를 이용하는 방법,

머 편한 방법을 택하면 된다.

주의 할 점은 <input type="file" name="file" size="50">
폼에서 파일프로퍼티 이름을 "file"로 썼다면, 
업로드될 파일에 접근 프로퍼티도 항상 무조건 네버! "file" 이 되어야 한다.
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
    MultipartHttpServletRequest 인터페이스는 스프링이 제공하는 인터페이스로,
    Multipart 요청이 들어올 때 내부적으로 원본 HttpServletRequest 대신 사용되는 인터페이스이다.
    MultipartHttpServletRequest 인터페이스는 실제로 어떤 메소드를 선언하고 있지 않으며, HttpServletRequest 인터페이스와 
    MultipartRequest 인터페이스를 상속받고 있다. 
    MultipartHttpServletRequest 인터페이스는 javax.servelt.HttpSerletRequest 인터페이스를 상속받기 때문에 웹 요청 정보를 구하기 위한
    getPartmeter()나 getHeader()와 같은 메소드를 사용할 수 있으며, 추가로 MultipartRequest 인터페이스가 제공하는 Multipart관련 메소드도 
    사용할 수 있다.



    -MultipartRequest 인터페이스의 파일 관련 주요 메소드
         Iterator<String> getFileNames() : 업로드 된 파일들의 이름 목록을 제공하는 Iterator를 구한다.
         MultipartFile getFile(String name) : 파라미터 이름이 name인 업로드 파일 정보를 구한다.
         List<MultipartFile> getFiles(String name) : 파라미터 이름이 name인 업로드 파일 정보 목록을 구한다.
         Map<String, MultipartFile> getFileMap() : 파라미터 이름을 Key로, 해당하는 파일 정보를 값으로 구한다.
	 */
	
	/*
	업로드한 파일을 특정파일로 저장하고 싶다면 MultipartFile.transferTo() 를 쓰면 편하다.
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
