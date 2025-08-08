package org.quarkus.openapi.generator.config.api.factory;

import java.util.Objects;
import jakarta.ws.rs.client.ClientBuilder;
import org.jboss.logging.Logger;
import org.quarkus.openapi.todo.api.ApiClient;
import org.slf4j.LoggerFactory;

public class ApiClientFactory {

    private ApiClientFactory() {
    }

    /**
     * Getting ApiClient configured for given tenant.
     *
     * @param basePath url path to given resource
     * @return {@link ApiClient}
     */
    public static ApiClient createTraceApiClient(String basePath) {
        ApiClient apiClient = new ApiClient().setBasePath(basePath);
        ClientBuilder clientBuilder = ClientBuilder.newBuilder()
            .register(apiClient.getJSON());

        if (LoggerFactory.getLogger(ApiClient.class).isDebugEnabled()) {
            clientBuilder.register(Logger.class);
        }

        return apiClient.setHttpClient(clientBuilder.build());
    }

    /**
     * Getting ApiClient configured for given tenant (or without tenant if tenantId is null).
     *
     * @param tenantId id of given tenant
     * @param basePath url path to given resource
     * @return {@link ApiClient}
     */
    public static ApiClient createTenantApiClient(String tenantId, String basePath) {
        ApiClient apiClient = createTraceApiClient(basePath);
        if (Objects.isNull(tenantId)) {
            return apiClient;
        }

        return apiClient.addDefaultHeader("X-TID", tenantId);
    }
}
