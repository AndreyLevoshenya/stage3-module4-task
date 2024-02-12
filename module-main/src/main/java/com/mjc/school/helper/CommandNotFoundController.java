package com.mjc.school.helper;

import com.mjc.school.controller.annotations.CommandHandler;
import org.springframework.stereotype.Controller;

import static com.mjc.school.helper.Constants.COMMAND_NOT_FOUND;

@Controller
public class CommandNotFoundController {
    @CommandHandler(operationNumber = -1)
    public String commandNotFound() {
        return COMMAND_NOT_FOUND;
    }
}
