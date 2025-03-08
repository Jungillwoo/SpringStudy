package spring.input;

import org.springframework.web.multipart.MultipartFile;

public class ImgVO {
    // 파라미터와 이름이 같은 멤버변수 선언
    private MultipartFile upload;

    public MultipartFile getUpload() {
        return upload;
    }

    public void setUpload(MultipartFile upload) {
        this.upload = upload;
    }
}
