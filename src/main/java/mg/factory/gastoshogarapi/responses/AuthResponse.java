package mg.factory.gastoshogarapi.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    String accessToken;
    String tokenType = "Bearer ";
}
