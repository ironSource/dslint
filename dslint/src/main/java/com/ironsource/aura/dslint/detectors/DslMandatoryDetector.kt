package com.ironsource.aura.dslint.detectors

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiClass
import com.intellij.psi.util.PropertyUtilBase
import com.ironsource.aura.dslint.DSLintAnnotation
import com.ironsource.aura.dslint.utils.resolveAttributeValue
import org.jetbrains.uast.*
import org.jetbrains.uast.util.isAssignment
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
class DslMandatoryDetector : DSLintDetector() {

    companion object {
        val ISSUE = Issue.create(
            "DslMandatory",
            "Mandatory DSL property not defined",
            "Mandatory DSL property not defined",
            Category.CORRECTNESS, 6,
            Severity.ERROR,
            Implementation(DslMandatoryDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun visitDslLintLambda(
        context: JavaContext,
        node: ULambdaExpression,
        dslLintClass: PsiClass
    ) {
        val dslPropertiesDefs = getDslMandatoryProperties(dslLintClass)

//        println("Defs = $dslPropertiesDefs")

        val dslPropertiesCalls = getDslMandatoryPropertiesCallsCount(
            dslPropertiesDefs,
            node.body as UBlockExpression
        )

//        println("Calls = $dslPropertiesCalls")

        // Report groups with no calls
        dslPropertiesCalls
            .filterValues { it == 0 }
            .forEach {
                context.report(
                    ISSUE, node, context.getLocation(node as UElement),
                    "\"${it.key}\" property must be defined"
                )
            }
    }

    // Returns mapping of group name to calls count
    private fun getDslMandatoryPropertiesCallsCount(
        dslProperties: Map<String, List<DSLMandatoryProperty>>,
        blockBody: UBlockExpression
    ): Map<String, Int> {
        val res = blockBody.expressions
            .filterIsInstance<UBinaryExpression>()
            .filter { it.isAssignment() }
            .mapNotNull {
                getPropertyGroup(getAssignedPropertyName(it), dslProperties)
            }
            .groupingBy { it }
            .eachCount()
            .toMutableMap()

        dslProperties.keys.forEach {
            res.putIfAbsent(it, 0)
        }

        return res
    }

    private fun getAssignedPropertyName(it: UBinaryExpression) =
        (((it.leftOperand as UReferenceExpression).referenceNameElement)
                as UIdentifier).name

    private fun getPropertyGroup(
        propertyName: String,
        dslProperties: Map<String, List<DSLMandatoryProperty>>
    ): String? {
        dslProperties.forEach { (group, properties) ->
            val propertiesName = properties.map { it.name }
            if (propertiesName.contains(propertyName)) {
                return group
            }
        }
        return null
    }

    // Return mapping of group name to mandatory properties, properties with no group are grouped by their name
    private fun getDslMandatoryProperties(clazz: PsiClass): Map<String, List<DSLMandatoryProperty>> {
        fun propertyNameFromSetter(setterName: String) = setterName.substring(3).decapitalize()

        return PropertyUtilBase.getAllProperties(clazz, true, false, true).values
            .filter {
                it.hasAnnotation(DSLintAnnotation.DslMandatory.name)
            }
            .map {
                val annotation = it.getAnnotation(DSLintAnnotation.DslMandatory.name)
                val group =
                    annotation.resolveAttributeValue<String>(DSLintAnnotation.DslMandatory.Attributes.group)
                DSLMandatoryProperty(
                    propertyNameFromSetter(it.name),
                    group
                )
            }
            .groupBy {
                if (!it.group.isNullOrEmpty()) it.group else it.name
            }
    }
}

data class DSLMandatoryProperty(
    val name: String,
    val group: String?
)