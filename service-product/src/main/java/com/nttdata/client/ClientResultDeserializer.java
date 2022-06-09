package com.nttdata.client;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.nttdata.client.dto.ClientResult;

import java.io.IOException;

public class ClientResultDeserializer extends JsonDeserializer<ClientResult> {
    @Override
    public ClientResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        ObjectCodec oc= jsonParser.getCodec();
        JsonNode node=oc.readTree(jsonParser);

        return new ClientResult(node.get("id").asText(),
                node.get("name").asText(),
                node.get("identificaiton").asText());
    }
}
