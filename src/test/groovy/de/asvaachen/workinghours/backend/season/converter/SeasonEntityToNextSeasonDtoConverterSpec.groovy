package de.asvaachen.workinghours.backend.season.converter

import de.asvaachen.workinghours.backend.project.persistence.SeasonEntity
import de.asvaachen.workinghours.backend.season.model.NextSeasonDto
import spock.lang.Specification
import spock.lang.Subject

class SeasonEntityToNextSeasonDtoConverterSpec extends Specification {

    Random random = new Random()

    @Subject
    SeasonEntityToNextSeasonDtoConverter converter = new SeasonEntityToNextSeasonDtoConverter()

    Integer year = random.nextInt()
    Integer obligatoryMinutes = random.nextInt()

    def 'convert a seasonEntity to a NextSeasonDto'() {
        given:
        SeasonEntity seasonEntity = new SeasonEntity(year: year, obligatoryMinutes: obligatoryMinutes)

        when:
        NextSeasonDto nextSeasonDto = converter.convert(seasonEntity)

        then:
        nextSeasonDto.nextSeason == year + 1
    }
}