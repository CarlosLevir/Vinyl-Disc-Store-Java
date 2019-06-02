import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException { 
		String email = (String) value;
		Pattern pattern = Pattern.compile ("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		
		boolean matchFound = matcher.matches();
		if (!matchFound) {
			FacesMessage facesMessage = new FacesMessage();
			facesMessage.setDetail("Email inválido.");
			facesMessage.setSummary("O email '" + email + "' infomado é inválido.");
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(facesMessage);
		}
	}
}
