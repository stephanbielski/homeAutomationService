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

    /**
     * The constructor used for the injection of the fields (constructor injection). (Direct) field injection is not
     * thread-safe here since this class is a Spring {@link Service} bean.
     *
     * @param request the {@link HttpServletRequest} object for providing meta information, never {@code null}
     */
    @Autowired
    public ErrorService(final HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Prepares the {@link Error} object with commonly used values.
     *
     * @param title         the error's title, must not be {@code null}
     * @param detail        the error's detailed description, may be {@code null}
     * @param invalidParams special field for parameter violations, may be {@code null}
     *
     * @return the final {@link Error}, never {@code null}
     */
    public Error finalizeRfc7807Error(final String title, final String detail,
                                      @Valid final List<Error.InvalidParam> invalidParams) {
        final UUID errorId = UUID.randomUUID();

        log.warn("Problems in request. ID: {}", errorId);

        return Error.builder().type(URI.create(request.getRequestURI())).title(title)
                .instance(URI.create("urn:ERROR:" + errorId)).detail(detail).invalidParams(invalidParams).build();
    }

    /**
     * Removes the Java package information from the error response body (fields {@code title} and {@code description}).
     *
     * @param errorMessage the plain exception localized message
     *
     * @return the 'cleaned' {@link String}
     */
    public static String removePackageInformation(final String errorMessage) {
        return RegExUtils.removeAll(errorMessage, "(de|com|org|io|net|\\@?javax?)(\\.\\p{IsLower}{2,20}){1,10}\\.");
    }
}
