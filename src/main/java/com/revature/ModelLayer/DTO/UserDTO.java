package com.revature.ModelLayer.DTO;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

//Dto for user authentication.
public class UserDTO {

    private static ObjectMapper mapper = new ObjectMapper();
    public String username;
    public String password;
    public String email;

    private static ObjectMapper jsonWriter = new ObjectMapper();

    public UserDTO() {
        super();
    }

    public UserDTO(HttpServletRequest req) throws IOException {

        UserDTO parsedDTO = null;
        String body = IOUtils.toString(req.getReader()); // getbody

        if (req != null && body != null && body.length() > 0) {

            parsedDTO = mapper.readValue(body, UserDTO.class);
            this.username = parsedDTO.username;
            this.password = parsedDTO.password;
            this.email = parsedDTO.email;
        }

    }

    public void WriteSelfAsJsonTokenToRequest(HttpServletResponse response) throws IOException {

        String selfAsJsonString = jsonWriter.writeValueAsString(this);

        response.getWriter().write(selfAsJsonString);

    }

}
