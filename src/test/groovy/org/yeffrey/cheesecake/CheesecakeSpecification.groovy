package org.yeffrey.cheesecake

import com.github.javafaker.Faker
import io.micronaut.context.ApplicationContext
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.Constants
import spock.lang.Shared
import spock.lang.Specification

import java.time.format.DateTimeFormatter

class CheesecakeSpecification extends Specification {
    @Inject
    ApplicationContext applicationContext

    @Shared
    Faker faker = new Faker()

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)

    UUID getBobUUID() {
        return UUID.fromString("8d9d461d-e78a-4c9d-a6ba-428a481b044d")
    }

}
