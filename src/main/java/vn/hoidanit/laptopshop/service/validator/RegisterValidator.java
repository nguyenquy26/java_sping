package vn.hoidanit.laptopshop.service.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.UserService;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Mat khau khong giong nhau").addPropertyNode("confirmPassword")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            valid = false;
        }

        if (userService.checkEmailExist(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email da ton tai").addPropertyNode("email")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            valid = false;
        }

        return valid;
    }

}
