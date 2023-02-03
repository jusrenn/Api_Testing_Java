package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HerokuAppTokenBody {
    /*
    {
        "username" : "admin",
        "password" : "password123"
    }
     */
    private String username;
    private String password;
}
