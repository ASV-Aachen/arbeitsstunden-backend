package de.asvaachen.workinghours.backend.season.converter

import de.asvaachen.workinghours.backend.project.persistence.SeasonEntity
import de.asvaachen.workinghours.backend.season.converter.SeasonDtoToSeasonEntityConverter
import de.asvaachen.workinghours.backend.season.model.SeasonDto
import spock.lang.Specification
import spock.lang.Subject

class SeasonDtoToSeasonEntityConverterSpec extends Specification {

    Random random = new Random()

    @Subject
    SeasonDtoToSeasonEntityConverter converter = new SeasonDtoToSeasonEntityConverter()

    Integer year = random.nextInt()
    String label = UUID.randomUUID().toString()
    Integer obligatoryMinutes = random.nextInt()

    def 'convert a SeasonDto to a SeasonEntity'() {
        given:
        SeasonDto seasonDto = new SeasonDto(year: year, label: label, obligatoryMinutes: obligatoryMinutes)

        when:
        SeasonEntity seasonEntity = converter.convert(seasonDto)

        then:
        seasonEntity.obligatoryMinutes == obligatoryMinutes
        seasonEntity.year == year
    }
}