package hello.login.web.border;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
