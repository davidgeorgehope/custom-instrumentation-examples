package org.davidgeorgehope;

/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */


import static net.bytebuddy.matcher.ElementMatchers.named;

import co.elastic.apm.agent.sdk.ElasticApmInstrumentation;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

public class WordCountInstrumentation extends ElasticApmInstrumentation {
    private static Logger logger = Logger.getLogger(WordCountInstrumentation.class.getName());

    @Override
    public ElementMatcher<? super TypeDescription> getTypeMatcher() {
        return named("org.davidgeorgehope.Main");
    }


    @Override
    public ElementMatcher<? super MethodDescription> getMethodMatcher() {
        return named("countWords");
    }

    @Override
    public Collection<String> getInstrumentationGroupNames() {
        return Collections.singletonList("elastic-plugin-example");
    }
    @Override
    public String getAdviceClassName() {
        return "org.davidgeorgehope.WordCountInstrumentation$AdviceClass";
    }

    @SuppressWarnings("unused")
    public static class AdviceClass {
        @Advice.OnMethodEnter(suppress = Throwable.class,inline = false)
        public static Object onEnter(@Advice.Argument(value = 0) String input) {
            Tracer tracer = GlobalOpenTelemetry.get().getTracer("instrumentation-library-name","semver:1.0.0");
            System.out.print("Entering method");

            // Start a new span
            Span span = tracer.spanBuilder("mySpan").setSpanKind(SpanKind.SERVER).startSpan();

            // Now the span is started and active.
            return span.makeCurrent(); // returning the scope to close it in the exit advice
        }

        @Advice.OnMethodExit(onThrowable = Throwable.class, suppress = Throwable.class,inline = false)
        public static void onExit(@Advice.Return(typing = Assigner.Typing.DYNAMIC) Object returnValue,
                                  @Advice.Thrown Throwable throwable,
                                  @Advice.Enter Object scopeObject) {
            System.out.print("return value"+returnValue);
            Span span = Span.current();

            // Handle potential error
            if (throwable != null) {
                span.setStatus(StatusCode.ERROR, "Exception thrown in method");
            } else {
               span.setAttribute("wordCount", (Integer)returnValue);
            }

            span.end();
            // Close the scope first
            Scope scope = (Scope) scopeObject;
            scope.close();
        }
    }
}