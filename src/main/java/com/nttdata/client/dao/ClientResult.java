package com.nttdata.client.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nttdata.client.ClientResultDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = ClientResultDeserializer.class)
public class ClientResult {
    String clientId;
    String name;

    /**
     *  VIP
     *  PYME
     *  NONE
     * */
    String profileType;

}
