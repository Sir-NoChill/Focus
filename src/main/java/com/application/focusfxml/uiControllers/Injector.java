package com.application.focusfxml.uiControllers;

import com.application.focusfxml.FocusApplication;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


//See https://edencoding.com/dependency-injection
public class Injector {

    /**
     * The classes that have specific controllers saved as a hashmap
     */
    private static final Map<Class<?>, Callback<Class<?>,Object>> injectionMethods = new HashMap<>();

    private static ResourceBundle bundle = null;

    /**
     *  A method to determine if a class-specific controller can be made
     *  if it can, we will return the custom controller
     *  if not we will return the default controller
     *
     * @param controllerClass the  class of the controller to be created
     * @return the created controller
     */
    private static Object constructController(Class<?> controllerClass) {
        if (injectionMethods.containsKey(controllerClass)) {
            return loadCustomController(controllerClass); //TODO this should load the controller for the controller class
        } else {
            return loadDefaultController(controllerClass); //TODO needs to return the default controller
        }
    }

    private static Object loadCustomController(Class<?> controller) {
        try {
            return injectionMethods.get(controller).call(controller);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static Object loadDefaultController(Class<?> controller) {
        try {
            return controller.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Parent load(String location) throws IOException {
        FXMLLoader loader = getLoader(location);
        return loader.load();
    }

    private static FXMLLoader getLoader(String location) {
        return new FXMLLoader(
                FocusApplication.class.getResource(location),
                bundle,
                new JavaFXBuilderFactory(),
                controllerClass -> constructController(controllerClass)
        );
    }

    public static void addInjectionMethod(Class<?> controller, Callback<Class<?>,Object> method) {
        injectionMethods.put(controller,method);
    }

    public void removeInjectionMethod(Class<?> controller) {
        injectionMethods.remove(controller);
    }

    ///// GETTERS AND SETTERS BELOW ///////////

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static void setBundle(ResourceBundle bundle) {
        Injector.bundle = bundle;
    }
}
