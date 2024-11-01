package org.woven.hrms;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.ANNOTATIONS;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.assignableTo;
import static com.tngtech.archunit.core.domain.JavaMember.Predicates.declaredIn;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.metaAnnotatedWith;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

@AnalyzeClasses(packages = "org.woven.hrms",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class HRMSArchitectureTests {
    @ArchTest
    private final ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    ArchRule controllerRule = classes().that(areControllerClasses())
            .should().haveSimpleNameEndingWith("Controller")
            .andShould().resideInAPackage("..controller")
            .as("Controller classes annotated with @RestController")
            .as("must reside in controller package ")
            .as("and have name ending with Controller");

    @ArchTest
    ArchRule controllerMethodsShouldBeAnnotated = methods().that(areControllerMethods())
            .should().beMetaAnnotatedWith(RequestMapping.class)
            .as("Controller methods annotated with any type of HTTP request mapping annotation")
            .as("e.g., @GetMapping, @PostMapping, @PutMapping, or @DeleteMapping");


    @ArchTest
    ArchRule serviceRule = classes().that(areServiceClasses())
            .should().haveSimpleNameEndingWith("Service")
            .andShould().resideInAPackage("..service")
            .as("Service classes annotated with @Service")
            .as("must reside in service package ")
            .as("and have name ending with Service");

    @ArchTest ArchRule repositoryRule = classes().that(areRepositoryClasses())
            .should().haveSimpleNameEndingWith("Repository")
            .andShould().resideInAPackage("..repository..")
            .as("Repository classes assignable from JpaRepository")
            .as("must reside in repository package")
            .as("and have name ending with Repository");

    @ArchTest
    ArchRule jpaRule = classes().that(areEntityClasses())
            .should().resideInAPackage("..entity");

    @ArchTest
    ArchRule controllerDependsOnService = classes().that(areControllerClasses())
            .should().accessClassesThat(areServiceClasses())
            .as("Controller access Service");

    @ArchTest
    ArchRule serviceDependsOnRepository = classes().that(areServiceClasses())
            .should().accessClassesThat(areRepositoryClasses())
            .as("Service access Repository");
    @ArchTest
    ArchRule repositoryDependsOnEntity = classes().that(areRepositoryClasses())
            .should().accessClassesThat(areEntityClasses())
            .as("Repository access Entity");

    @ArchTest
    ArchRule layeredArchRule = layeredArchitecture().consideringOnlyDependenciesInLayers()
            .layer("Entity").definedBy(areEntityClasses())
            .layer("Repository").definedBy(areRepositoryClasses())
            .layer("Service").definedBy(areServiceClasses())
            .layer("Controller").definedBy(areControllerClasses())
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Controller").mayOnlyAccessLayers("Service","Entity")
            .whereLayer("Service").mayOnlyAccessLayers("Repository", "Entity")
            .whereLayer("Repository").mayOnlyAccessLayers("Entity")
            .as("Layered Architecture Rules");


    private static DescribedPredicate<JavaClass> areControllerClasses() {
        return are(not(ANNOTATIONS).and(metaAnnotatedWith(RestController.class)));
    }

    private static DescribedPredicate<JavaMember> areControllerMethods() {
        return are(declaredIn(areControllerClasses()));
    }

    private static DescribedPredicate<JavaClass> areServiceClasses() {
        return are(not(ANNOTATIONS).and(metaAnnotatedWith(Service.class)));
    }

    private static DescribedPredicate<JavaClass> areRepositoryClasses() {
        return are(not(ANNOTATIONS).and(assignableTo(JpaRepository.class)));
    }

    private static DescribedPredicate<JavaClass> areEntityClasses() {
        return are(not(ANNOTATIONS).and(metaAnnotatedWith(Entity.class)));
    }

}
