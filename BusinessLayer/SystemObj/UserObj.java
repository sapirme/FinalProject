package SystemObj;

public class UserObj {
    private String email;
    private static UserObj INSTANCE = null;

    private UserObj(){}

    public UserObj getUser(){
        if (INSTANCE == null){
            INSTANCE = new UserObj();
        }
        return INSTANCE;
    }
}
