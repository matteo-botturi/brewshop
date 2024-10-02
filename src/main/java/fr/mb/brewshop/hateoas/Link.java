package fr.mb.brewshop.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.net.URI;

@AllArgsConstructor
@Getter
@Setter
public class Link {
    private String name;
    private String method;
    private URI uri;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object parameter;

    public Link(String name, String httpMethod, URI uri) {
        this.name = name;
        this.method = httpMethod;
        this.uri = uri;
    }
}