package org.umer;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.umer.domain.Fruit;
import org.umer.service.FruitService;

@Path("/fruits")
public class FruitResource {

    @Inject
    FruitService service;

    @GET
    public List<Fruit> getFruits() {
        try {
            return service.list();
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @POST
    @Operation(summary = "adds a new fruit")
    public Response saveFruit(Fruit fruit) {
        try {
            return Response.ok(service.add(fruit)).build();
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }


}