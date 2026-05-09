package camix.service;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Extension JUnit 5 inspiree de la demonstration officielle de Sam Brannen :
 * ignorer un test/nested marque quand un contexte englobant a deja echoue.
 */
class SkipOnFailureInEnclosingClassExtension implements ExecutionCondition, TestWatcher
{
    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(SkipOnFailureInEnclosingClassExtension.class);
    private static final String FAILED_CONTEXTS_KEY = "failed-contexts";

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context)
    {
        if (!isMarkedToSkipOnFailure(context)) {
            return ConditionEvaluationResult.enabled("No @SkipOnFailureInEnclosingClass marker found.");
        }

        if (hasFailedAncestor(context)) {
            return ConditionEvaluationResult.disabled("Skipped due to failure in an enclosing context.");
        }

        return ConditionEvaluationResult.enabled("No enclosing failure detected.");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause)
    {
        final Set<String> failedContexts = failedContexts(context);

        Optional<ExtensionContext> current = Optional.of(context);
        while (current.isPresent()) {
            failedContexts.add(current.get().getUniqueId());
            current = current.get().getParent();
        }
    }

    private boolean hasFailedAncestor(ExtensionContext context)
    {
        final Set<String> failedContexts = failedContexts(context);

        Optional<ExtensionContext> parent = context.getParent();
        while (parent.isPresent()) {
            if (failedContexts.contains(parent.get().getUniqueId())) {
                return true;
            }
            parent = parent.get().getParent();
        }
        return false;
    }

    private boolean isMarkedToSkipOnFailure(ExtensionContext context)
    {
        Optional<ExtensionContext> current = Optional.of(context);

        while (current.isPresent()) {
            if (current.get().getElement()
                    .map(element -> element.isAnnotationPresent(SkipOnFailureInEnclosingClass.class))
                    .orElse(false)) {
                return true;
            }
            current = current.get().getParent();
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private Set<String> failedContexts(ExtensionContext context)
    {
        return context.getRoot()
                .getStore(NAMESPACE)
                .getOrComputeIfAbsent(FAILED_CONTEXTS_KEY,
                        key -> ConcurrentHashMap.newKeySet(), Set.class);
    }
}
