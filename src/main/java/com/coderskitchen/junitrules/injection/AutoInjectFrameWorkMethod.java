package com.coderskitchen.junitrules.injection;

import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Method;

public class AutoInjectFrameWorkMethod extends FrameworkMethod {
  /**
   * Returns a new {@code FrameworkMethod} for {@code method}
   */
  public AutoInjectFrameWorkMethod(final Method method) {
    super(method);
  }
}
