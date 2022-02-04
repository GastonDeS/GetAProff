package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.dto.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("images")
@Component
public class ImagesController {

    @Autowired
    private ImageService imageService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getImage(@PathParam("id") Long id) {
        Optional<Image> image = imageService.findImageById(id);
        return image.isPresent() ? Response.ok(ImageDto.fromUser(uriInfo, image.get())).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

//    @RequestMapping(value = "/image/{uid}", method = RequestMethod.GET, produces = "image/*")
//    public @ResponseBody
//    byte[] getImage(@PathVariable("uid") final Long uid) {
//        Optional<Image> image =  imageService.findImageById(uid);
//        return image.map(Image::getImage).orElse(null);
//    }

}
