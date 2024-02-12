package com.mjc.school.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class CommandSender {

    private final Map<String, Object> controllers;
    private final ObjectMapper objectMapper;
    private final Map<Class<?>, Function<String, Object>> stringToObjectMapper;

    @Autowired
    public CommandSender(ListableBeanFactory listableBeanFactory) {
        controllers = listableBeanFactory.getBeansWithAnnotation(Controller.class);
        objectMapper = new ObjectMapper();
        stringToObjectMapper = new HashMap<>();
        stringToObjectMapper.put(Long.class, Long::valueOf);
    }

    public Object send(Command command) throws Exception {
        if(command == null) {
            throw new NullPointerException("command is null");
        }

        CommandHandlerWithController commandHandlerWithController = getCommandHandlerWithControllers(command.operation());

        Object controller = commandHandlerWithController.controller;
        Method commandHandler = commandHandlerWithController.method;

        try {
            return commandHandler.invoke(controller, getMethodArgs(command, commandHandler));
        } catch (InvocationTargetException e) {
            if (e.getTargetException() != null) {
                throw (Exception) e.getTargetException();
            } else {
                throw e;
            }
        }

    }

    private CommandHandlerWithController getCommandHandlerWithControllers(int operation) {
        List<CommandHandlerWithController> listOfCommandHandlers = new ArrayList<>();

        for (Object controller : controllers.values()) {
            List<CommandHandlerWithController> handlersWithController = Stream.of(controller.getClass().getDeclaredMethods())
                    .filter(m -> !m.isBridge())
                    .filter(m -> m.isAnnotationPresent(CommandHandler.class))
                    .filter(m -> m.getAnnotation(CommandHandler.class).operationNumber() == operation)
                    .map(m -> new CommandHandlerWithController(controller, m)).toList();
            listOfCommandHandlers.addAll(handlersWithController);
        }

        if(listOfCommandHandlers.size() != 1) {
            throw new RuntimeException(String.format("handlers were not found for operation: '%d'", operation));
        }
        return listOfCommandHandlers.get(0);
    }

    Object[] getMethodArgs(Command command, Method method) throws JsonProcessingException {
        List<Object> args = new ArrayList<>();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for(Annotation annotation : parameterAnnotations[i]) {
                if(annotation instanceof CommandBody) {
                    Parameter parameter = method.getParameters()[i];
                    args.add(objectMapper.readValue(command.body(), parameter.getType()));
                }
                else if(annotation instanceof CommandParam an && command.param() != null) {
                    Parameter parameter = method.getParameters()[i];
                    String value = command.param().get(an.name());
                    args.add(stringToObjectMapper.get(parameter.getType()).apply(value));
                }
            }
        }
        return args.toArray();
    }

    private record CommandHandlerWithController(Object controller, Method method){

    }
}
