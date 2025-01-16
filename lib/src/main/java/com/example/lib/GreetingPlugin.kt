package com.example.lib

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property



class GreetingPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        val androidComponents = p0.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants {
            it.transformClassesWith()
        }
    }
}

class GreetTest: AsmClassVisitorFactory {

}


interface GreetingPluginExtension {
    val message: Property<String>
    val greeter: Property<String>
}