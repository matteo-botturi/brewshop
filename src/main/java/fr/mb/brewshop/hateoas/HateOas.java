package fr.mb.brewshop.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HateOas {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Link> links = new ArrayList<>();

    public void addLink(String name, String httpMethod, URI uri) {
        links.add(new Link(name, httpMethod, uri));
    }

    public void addLink(String name, String httpMethod, URI uri, Object parameter) {
        links.add(new Link(name, httpMethod, uri, parameter));
    }
}