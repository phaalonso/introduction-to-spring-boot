package alonso.pedro.products.handler;

import alonso.pedro.products.dtos.ErroDTO;
import alonso.pedro.products.dtos.MensagemDTO;
import alonso.pedro.products.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    private static final String UNEXPECTED_ERROR = "Exception.unexpected";

    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDTO> handle(MethodArgumentNotValidException exception) {
        List<FieldError> erros = exception.getBindingResult().getFieldErrors();

        return erros.stream().map(err -> {
            String mensagem = messageSource.getMessage(err, LocaleContextHolder.getLocale());

            return new ErroDTO(err.getField(), mensagem);
        }).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public MensagemDTO handleBusinessException(BusinessException exception) {
        return new MensagemDTO(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception ex) {
        ex.printStackTrace();
    }
}
