package org.davidgeorgehope;

/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */


import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.logging.Logger;

public class WordCountInstrumentation implements TypeInstrumentation {
    private static Logger logger = Logger.getLogger(WordCountInstrumentation.class.getName());


    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {

        logger.info("TEST typeMatcher");
        return ElementMatchers.named("org.davidgeorgehope.Main");
    }

    @Override
    public void transform(TypeTransformer typeTransformer) {
        logger.info("TEST transform");
        typeTransformer.applyAdviceToMethod(namedOneOf("countWords"),this.getClass().getName() + "$WordCountAdvice");
    }

    @SuppressWarnings("unused")
    public static class WordCountAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static Scope onEnter(@Advice.Argument(value = 0) String input, @Advice.Local("otelSpan") Span span) {
            Tracer tracer = GlobalOpenTelemetry.getTracer("instrumentation-library-name","semver:1.0.0");
            System.out.print("Entering method");

            // Start a new span
            span = tracer.spanBuilder("mySpan").startSpan();

            // Make the span active
            Scope scope = span.makeCurrent();

            // Now the span is started and active.
            return scope; // returning the scope to close it in the exit advice
        }

        @Advice.OnMethodExit(onThrowable = Throwable.class, suppress = Throwable.class)
        public static void onExit(@Advice.Return(readOnly = false) int wordCount,
                                  @Advice.Thrown Throwable throwable,
                                  @Advice.Local("otelSpan") Span span,
                                  @Advice.Enter Scope scope) {
            // Close the scope first
            scope.close();

            // Handle potential error
            if (throwable != null) {
                span.setStatus(StatusCode.ERROR, "Exception thrown in method");
            } else {
                span.setAttribute("wordCount", wordCount);
            }

            // End the span
            span.end();
        }
    }
}