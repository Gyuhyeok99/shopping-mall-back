package hello.login.domain.member;

public enum GenderType {

    MAN("남성"), WOMAN("여성");
    private final String sex;

    GenderType(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }




}
