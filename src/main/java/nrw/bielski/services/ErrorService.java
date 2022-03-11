package nrw.bielski.services;

import lombok.extern.log4j.Log4j2;
import nrw.bielski.personenservice.model.Error;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class ErrorService {

    private final HttpServletRequest request;

    @Autowired
    public ErrorService(final HttpServletRequest request) {
        this.request = request;
    }

    public Error finalizeRfc7807Error(final String title, final String detail,
                                      @Valid final List<Error.InvalidParam> invalidParams) {
        final UUID errorId = UUID.randomUUID();

        log.warn("Problems in request. ID: {}", errorId);

        return Error.builder().type(URI.create(request.getRequestURI())).title(title)
                .instance(URI.create("urn:ERROR:" + errorId)).detail(detail).invalidParams(invalidParams).build();
    }

    public static String removePackageInformation(final String errorMessage) {
        return RegExUtils.removeAll(errorMessage, "(de|com|org|io|net|\\@?javax?)(\\.\\p{IsLower}{2,20}){1,10}\\.");
    }
}
