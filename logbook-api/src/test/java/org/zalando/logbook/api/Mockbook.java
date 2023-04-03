package org.zalando.logbook.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@AllArgsConstructor
@Getter
final class Mockbook implements Logbook {

    private final Predicate<HttpRequest> predicate;
    private final CorrelationId correlationId;
    private final QueryFilter queryFilter;
    private final PathFilter pathFilter;
    private final HeaderFilter headerFilter;
    private final BodyFilter bodyFilter;
    private final RequestFilter requestFilter;
    private final ResponseFilter responseFilter;
    private final Strategy strategy;
    private final Sink sink;

    @Override
    public RequestWritingStage process(final HttpRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestWritingStage process(final HttpRequest request, final Strategy strategy) {
        throw new UnsupportedOperationException();
    }

}
