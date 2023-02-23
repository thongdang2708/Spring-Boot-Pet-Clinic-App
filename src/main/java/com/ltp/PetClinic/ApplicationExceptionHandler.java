package com.ltp.PetClinic;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ltp.PetClinic.exception.ErrorResponse;
import com.ltp.PetClinic.exception.InvalidInputDueToEnum;
import com.ltp.PetClinic.exception.InvalidRefreshTokenException;
import com.ltp.PetClinic.exception.OwnerNotFoundWithIdException;
import com.ltp.PetClinic.exception.PetNotFoundWithIdsException;
import com.ltp.PetClinic.exception.RoleNotFoundWithIdException;
import com.ltp.PetClinic.exception.RoleNotFoundWithNameException;
import com.ltp.PetClinic.exception.UserNotFoundWithIdException;
import com.ltp.PetClinic.exception.UserNotFoundWithNameException;
import com.ltp.PetClinic.exception.UsernameExistsException;
import com.ltp.PetClinic.exception.VetNotFoundWithIdException;
import com.ltp.PetClinic.exception.VisitExistsWithOwnerIdAndPetIdException;
import com.ltp.PetClinic.exception.VisitNotFoundWithIdException;
import com.ltp.PetClinic.exception.VisitNotFoundWithIdsException;
import com.ltp.PetClinic.exception.WrongBetweenOwnerAndPetException;
import com.ltp.PetClinic.storageObject.MessageExceptionObject;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UserNotFoundWithIdException.class, RoleNotFoundWithNameException.class,
            RoleNotFoundWithIdException.class, UserNotFoundWithNameException.class,
            OwnerNotFoundWithIdException.class, PetNotFoundWithIdsException.class, VetNotFoundWithIdException.class,
            VisitNotFoundWithIdsException.class, WrongBetweenOwnerAndPetException.class,
            VisitExistsWithOwnerIdAndPetIdException.class, VisitNotFoundWithIdException.class })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ InvalidRefreshTokenException.class })
    public ResponseEntity<Object> handleInvadidRefreshToken(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ UsernameExistsException.class, InvalidInputDueToEnum.class })
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
