package SystemObj;

public class UserObj {
    private String email ;
    private String id_token ;

    public UserObj(){
        email = null;
        id_token = null;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setToken(String id_token){
        this.id_token = id_token;
    }

    public String getToken(){
        return this.id_token;
    }

    @Override
    public String toString() {
        return "email: " + this.email + " id token: "+ this.id_token ;
    }
}
