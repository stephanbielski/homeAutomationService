package nrw.bielski.personenservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.List;

//@Builder
public record Error(@NotNull URI type, @NotNull @Size(min = 5, max = 50) String title, @NotNull URI instance,
                    @Size(min = 10, max = 200) String detail,
                    @JsonProperty("invalid_params") @Valid List<InvalidParam> invalidParams) {

    //@Builder
    public static record InvalidParam(@NotNull @Size(min = 3, max = 20) String name,
                                      @NotNull @Size(min = 8, max = 200) String reason) {
    }
}
