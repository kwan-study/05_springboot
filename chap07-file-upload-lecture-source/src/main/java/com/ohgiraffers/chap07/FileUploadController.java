package com.ohgiraffers.chap07;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class FileUploadController {

    /* 설명. build된 곳에 파일 업로드 경로를 지정하기 위해 ResourceLoader 의존성 주입 받기 */
    private ResourceLoader resourceLoader;

    @Autowired
    public FileUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /* 설명. multipart/form-data로 넘어온 정보는 MultipartFile로 받아낸다. */
    @PostMapping("single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile
            , @RequestParam String singleFileDescription
            , RedirectAttributes rttr) throws IOException {
//        System.out.println("singleFile = " + singleFile);
//        System.out.println("singleFileDescription = " + singleFileDescription);

        Resource resource = resourceLoader.getResource("classpath:static/uploadFiles/img/single");
        System.out.println("파일 업로드 할 폴더의 절대 경로: " + resource.getFile().getAbsolutePath());

        String filePath = resource.getFile().getAbsolutePath();

        String originFileName = singleFile.getOriginalFilename();
        System.out.println("originFileName = " + originFileName);

        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        System.out.println("ext = " + ext);

        String saveName = UUID.randomUUID().toString().replace("-", "") + ext;
        System.out.println("saveName = " + saveName);

        /* 설명. 1. 지정된 위치에 업로드된 파일 저장(feat.예외처리), 2. DB에 저장할 정보 추출(DB 모델링) */
        try {
            singleFile.transferTo(new File(filePath + "/" + saveName));

            /* 설명. 원래라면 이 부분에서 BL 구문 작성(서비스 계층 <-> DB) */
            /* 설명. BL이 성공하면 redirect 된 페이지를 값을 넘기기 위해 RedirectAttributes로 담는다.(flashAttribute) */
            rttr.addFlashAttribute("message", "파일 업로드 성공!");
            rttr.addFlashAttribute("img", "uploadFiles/img/single/" + saveName);
            rttr.addFlashAttribute("singleFileDescription", singleFileDescription);

        } catch (Exception e) {
            new File(filePath + "/" + saveName).delete();
        }

        return "redirect:/result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(@RequestParam List<MultipartFile> multiFiles
            , @RequestParam String multiFileDescription
            , RedirectAttributes rttr) throws IOException {

        String filePath = resourceLoader.getResource("classpath:static/uploadFiles/img/multi")
                .getFile()
                .getAbsolutePath();

        List<Map<String, String>> files = new ArrayList<>();        // DB에 저장할 값들을 지닌 list(Service 계층으로 넘길 값)
        List<String> saveFiles = new ArrayList<>();                 // 화면단에서 img 태그가 참조할 정적 리소스 경로(src속성)

        try {
            for (int i = 0; i < multiFiles.size(); i++) {
                String originFileName = multiFiles.get(i).getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String saveName = UUID.randomUUID().toString().replace("-", "") + ext;

                /* 설명. DB의 한 행에 들어갈 내용을 하나의 Map으로 추출(DB모델링 참고할 것) */
                Map<String, String> file = new HashMap<>();
                file.put("originFileName", originFileName);
                file.put("saveName", saveName);
                file.put("filePath", filePath);
                file.put("multiFileDescription", multiFileDescription);

                files.add(file);

                multiFiles.get(i).transferTo(new File(filePath + "/" + saveName));
                saveFiles.add("uploadFiles/img/multi/" + saveName);
            }

            /* 설명. 여기까지 문제가 없었다면 성공 시 가져갈 화면의 재료 준비 */
            rttr.addFlashAttribute("message", "다중 파일 업로드 성공!");
            rttr.addFlashAttribute("imgs", saveFiles);
            rttr.addFlashAttribute("multiFileDescription", multiFileDescription);
        } catch (Exception e) {

            /* 설명. 전체 파일 업로드가 아닌 일부 파일 업로드 시 올라간 파일 삭제 코드 */
            for(int i = 0; i < saveFiles.size(); i++) {       // saveFiles는 문제 없이 파일 업로드 되었던 파일 경로가 담겨있음
                Map<String, String> file = files.get(i);
                new File(filePath + "/" + file.get("saveName")).delete();
            }

            rttr.addFlashAttribute("message", "다중 파일 업로드 실패");
        }

        return "redirect:/result";
    }

    @GetMapping("/result")
    public void result() {}
}