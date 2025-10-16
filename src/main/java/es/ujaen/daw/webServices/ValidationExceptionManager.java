package es.ujaen.daw.webServices;

import es.ujaen.daw.model.dto.ErrorValidacion;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ValidationExceptionManager implements ExceptionMapper<ConstraintViolationException> {


    @Override
    public Response toResponse(ConstraintViolationException e) {
        //convert bean-validation contstraintviolation set to json array
        //p.e. [{"name":"age", "message":"the age must be over 18"}]

        List<Object> errors = new ArrayList<>();

        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {

            //attribute name is the last part, e.g. method.arg0.propname
            String[] parts=cv.getPropertyPath().toString().split("\\.");

            //Using DTO record for bean validation message (jdk 14+)
            errors.add( new ErrorValidacion(parts[parts.length-1], cv.getMessage() ) );
        }
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(errors)
                .build();
    }
}
