package com.nttdata.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    /**
     * id del cliente del microservicio de cliente
     * */
    private String clientId;
}
