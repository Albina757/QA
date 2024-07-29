package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder

@Data
@AllArgsConstructor
@NoArgsConstructor


public class UpdateUserRequest {

        private String lastName;
        private String firstName;
}
