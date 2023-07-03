package Person.person.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonRequest {
    @NotBlank(message = "Name cant be blank")
    @NotEmpty
    @Size(min = 5, message = "Name at least with five characters")
    private String firstN;

    @NotBlank(message = "Must have non blank values")
    @NotEmpty(message = "Please fill")
    private String lastN;

    public String getFirstN() {
        return firstN;
    }

    public void setFirstN(String firstN) {
        this.firstN = firstN;
    }

    public String getLastN() {
        return lastN;
    }

    public void setLastN(String lastN) {
        this.lastN = lastN;
    }
}
