package org.umer;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.umer.domain.Fruit;
import org.umer.service.FruitService;

@Path("/hello")
public class GreetingResource {

    @Inject
    FruitService service;



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/fruits")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Fruit> getFruits() {
        try {
            return service.list();
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }
}