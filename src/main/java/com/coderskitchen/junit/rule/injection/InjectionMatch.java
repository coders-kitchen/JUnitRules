package com.coderskitchen.junit.rule.injection;

import java.lang.reflect.Field;

public class InjectionMatch {
  public final Field mockField;
  public final Field classField;

  public InjectionMatch(final Field mockField, final Field classField) {
    this.mockField = mockField;
    this.classField = classField;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("InjectionMatch{");
    sb.append("mockField='").append(mockField.getName()).append('\'');
    sb.append(", classField='").append(classField.getName()).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final InjectionMatch that = (InjectionMatch) o;

    if (classField != null ? !classField.equals(that.classField) : that.classField != null) {
      return false;
    }
    if (mockField != null ? !mockField.equals(that.mockField) : that.mockField != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = mockField != null ? mockField.hashCode() : 0;
    result = 31 * result + (classField != null ? classField.hashCode() : 0);
    return result;
  }
}
