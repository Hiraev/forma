import tools.forma.android.feature.AndroidLibraryFeatureConfiguration
import tools.forma.android.feature.androidLibraryFeatureDefinition
import tools.forma.android.feature.applyFeatures
import tools.forma.android.feature.kotlinAndroidFeatureDefinition
import tools.forma.android.target.AndroidUtilTarget
import tools.forma.android.target.ResourcesTarget
import tools.forma.android.target.TestUtilTarget
import tools.forma.android.owner.NoOwner
import tools.forma.android.owner.Owner
import tools.forma.android.validation.validate
import tools.forma.android.validation.validator
import tools.forma.android.validation.disallowResources
import tools.forma.android.visibility.Public
import tools.forma.android.visibility.Visibility
import org.gradle.api.Project
import tools.forma.android.dependencies.applyDependencies


/**
 * TODO
 *
 * External AAR Libraries extensions - usage:
 * Add libraries to dependencies e.g. appcompat
 * retrofit dependency cluster should include extension
 *
 * ```
 * val retrofit = transitiveDeps(
 *     "com.google.design:material:2.8.0"
 * ) + deps(
 *     project("material-android-util")
 * )
 * ```
 *
 */
fun Project.androidUtil(
    packageName: String,
    owner: Owner = NoOwner,
    visibility: Visibility = Public,
    dependencies: FormaDependency = emptyDependency(),
    testDependencies: FormaDependency = emptyDependency()
) {

    disallowResources()

    //TODO unify with util, use androidJar dependency
    validate(AndroidUtilTarget)

    val androidFeatureConfig = AndroidLibraryFeatureConfiguration(
        packageName
    )

    applyFeatures(
        androidLibraryFeatureDefinition(androidFeatureConfig),
        kotlinAndroidFeatureDefinition()
    )

    applyDependencies(
        validator = validator(
            AndroidUtilTarget,
            TestUtilTarget,
            ResourcesTarget
        ),
        dependencies = dependencies,
        testDependencies = testDependencies
    )
}