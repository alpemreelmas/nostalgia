package org.nostalgia.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for NostalgiaOpenAPI.
 * This class provides a custom OpenAPI configuration for the Nostalgia software solutions.
 */
@Configuration
class NostalgiaOpenAPIConfiguration {

    @Value("${info.application.name}")
    private String title;

    @Value("${info.application.version}")
    private String version;

    /**
     * Creates a custom OpenAPI instance for AYS.
     *
     * @return The custom OpenAPI instance.
     */
    /*@Bean
    public OpenAPI openAPI() {

        final Contact contact = new Contact()
                .name(" with AYS Software Solutions")
                .email("iletisim@afetyonetimsistemi.org");

        final Info info = new Info()
                .title(this.title)
                .version(this.version)
                .description("Nostalgia | Nostalgia BE APIs Documentation")
                .contact(contact);

        return new OpenAPI().info(info);
    }*/

}
