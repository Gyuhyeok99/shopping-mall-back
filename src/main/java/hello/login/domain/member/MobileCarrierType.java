package hello.login.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MobileCarrierType {

    private String code;
    private String displayName;
}
