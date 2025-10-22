package com.system.vetcare.payload.response;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import com.system.vetcare.domain.Veterinarian;
import lombok.Getter;

@Getter
public class VeterinarianResponse {
       
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String phone; 
    private final String educationLevel;
    private final String totalExperience;
    
    public static final String YEARS_AND_MOTHS_FORMAT = "Years: %s Months: %s";
    public static final String THE_EXPERIENCE_IS_NOT_PRESENT_NOTIFICATION = "The experience is not present";
    
    public VeterinarianResponse(Veterinarian veterinarian) {
        this.id = veterinarian.getId();
        this.firstName = veterinarian.getStaff().getUser().getFirstName();
        this.lastName = veterinarian.getStaff().getUser().getLastName();
        this.phone = veterinarian.getStaff().getUser().getPhone();
        this.educationLevel = veterinarian.getStaff().getEducationLevel().name();
        this.totalExperience = convertExperienceToYearsAndMonths(
            veterinarian.getStaff().getTotalMonthsOfExperience()
        );
    }
   
    private String convertExperienceToYearsAndMonths(Integer totalMonthsOfExperience) {
        if (nonNull(totalMonthsOfExperience)) {
            Integer years = totalMonthsOfExperience / 12;
            Integer months = totalMonthsOfExperience % 12;
            return format(YEARS_AND_MOTHS_FORMAT, years, months);
        } else {
            return THE_EXPERIENCE_IS_NOT_PRESENT_NOTIFICATION;
        }
    }
    
}