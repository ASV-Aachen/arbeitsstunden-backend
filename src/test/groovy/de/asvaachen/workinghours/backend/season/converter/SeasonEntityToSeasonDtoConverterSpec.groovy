package de.asvaachen.workinghours.backend.season.converter

import de.asvaachen.workinghours.backend.project.persistence.SeasonEntity
import de.asvaachen.workinghours.backend.season.model.SeasonDto
import spock.lang.Specification
import spock.lang.Subject

class SeasonEntityToSeasonDtoConverterSpec extends Specification {

    Random random = new Random()

    @Subject
    SeasonEntityToSeasonDtoConverter converter = new SeasonEntityToSeasonDtoConverter()

    Integer year = random.nextInt()
    Integer obligatoryMinutes = random.nextInt()

    def 'convert a SeasonEntity to a SeasonDto'() {
        given:
        SeasonEntity seasonEntity = new SeasonEntity(year: year, obligatoryMinutes: obligatoryMinutes)

        when:
        SeasonDto seasonDto = converter.convert(seasonEntity)

        then:
        seasonDto.year == year
        seasonDto.obligatoryMinutes == obligatoryMinutes
        seasonDto.label == "${year - 1}/${year}"
    }
}