package model.repository;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD,
        ElementType.TYPE, ElementType.PARAMETER})
// @Target allow to specify which kind of java element this annotation is valid to used on.
// Like only can used in class
// Element.Type mean this annotation can used in class
// Element.Method allow annotation used in Method

// @Retention(RetentionPolicy.RUNTIME) tell java keep this annotation around through actual running of program
// so that other code can look at annotation while running.
// 99 percent of the time will used this to create annotation so Just specify this everytime.


public @interface PostGresDatabase {}
// @interface mean create annotation