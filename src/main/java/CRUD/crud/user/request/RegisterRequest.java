package CRUD.crud.user.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String email;
    private String fname;
    private String lname;
    private String password;
}
