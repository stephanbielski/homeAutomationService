package nrw.bielski.services;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import nrw.bielski.model.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

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

        return new Error(URI.create(request.getRequestURI())
                ,title
                ,URI.create(request.getRequestURI())
                ,detail
                ,invalidParams);
    }
}