package base.project.restapi.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.context.MessageSource;

@Component
public class MessageHelper {
    MessageSource messageSource;

    @Autowired
    public MessageHelper(
        MessageSource messageSource
    ) {
        this.messageSource = messageSource;
    }

    public String responseMessage(String key) {
        return this.messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
